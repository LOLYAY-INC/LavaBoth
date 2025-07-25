package io.lolyay.lavaboth.backends.lavalinkclient.search;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.search.LavaPlayerSearchManager;
import io.lolyay.lavaboth.backends.lavaplayer.search.LavaResultHandler;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.search.searchers.DefaultSearcher;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.utils.Logger;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LavaLinkSearchManager extends AbstractSearchManager {
    private final ArrayList<AbstractSearcher> searchers = new ArrayList<>();

    public LavaLinkSearchManager(LavaLinkPlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public CompletableFuture<Search> Internalsearch(String query, RequestorData requestorData) {
        CompletableFuture<Search> future = new CompletableFuture<>();
        submit(() -> trySearcherAtIndex(query, requestorData, 0, future),"Search-Slave-Link-");
        return future;
    }

    /**
     * Tries to find a result using the searcher at the given index.
     * If it fails, it recursively calls itself with the next index.
     *
     * @param index      The index of the searcher to try in the `searchers` list.
     * @param mainFuture The main future to complete with the final result.
     */
    private void trySearcherAtIndex(String query, RequestorData requestorData, int index, CompletableFuture<Search> mainFuture) {
        // BASE CASE: If we've run out of searchers, the search has failed.
        if (index >= searchers.size()) {
            mainFuture.complete(Search.wasNotFound(Search.SearchResult.ERROR("No searcher found a result for: " + query), "LavaPlayer", query));
            return; // Stop the recursion.
        }
        if(!searchers.get(index).canSearch(query)) {
            trySearcherAtIndex(query, requestorData, index + 1, mainFuture);
        }
        try {

            AbstractSearcher currentSearcher = searchers.get(index);
            final String xquery;
            if (currentSearcher instanceof DefaultSearcher)
                xquery = query.replace("https://", "").replace("http://", "");
            else
                xquery = query;
            // Perform the search with this single searcher. This returns a future immediately.
            CompletableFuture<Search> singleSearchFuture = searchWith(new LinkQuery(requestorData, xquery, currentSearcher));

            singleSearchFuture.whenComplete((result, exception) -> {
                if (exception == null && result != null && result.result().isSuccess()) {
                    mainFuture.complete(result);
                } else {
                    trySearcherAtIndex(xquery, requestorData, index + 1, mainFuture);
                }
            });
        } catch (Exception e) {
            Logger.err(e.getMessage());
            e.printStackTrace();
            mainFuture.completeExceptionally(e);
        }
    }

    /**
     * Performs a search with a single searcher and returns a future for its result.
     */
    private CompletableFuture<Search> searchWith(LinkQuery lavaQuery) {
            CompletableFuture<Search> future = new CompletableFuture<>();
        try {
            ((LavaLinkPlayerManager) getPlayerManager()).getJLavalinkClient().loadTracks(lavaQuery.getSource().getPrefix() + lavaQuery.getQuery()).thenAccept(
                    new LinkResultHandler(future, (LavaLinkPlayerManager) getPlayerManager(), lavaQuery)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }


    @Override
    public void registerSearcher(AbstractSearcher searcher) {
        searchers.add(searcher);
    }

    @Override
    public void unregisterSearcher(AbstractSearcher searcher) {
        searchers.remove(searcher);
    }


    public static class LinkQuery {
        private final RequestorData requestorData;
        private final String query;
        private final AbstractSearcher source;
        public LinkQuery(RequestorData requestorData, String query, AbstractSearcher source) {
            this.requestorData = requestorData;
            this.query = query;
            this.source = source;
        }


        public RequestorData getRequestorData() {
            return requestorData;
        }

        public String getQuery() {
            return query;
        }


        public AbstractSearcher getSource() {
            return source;
        }
    }
}
