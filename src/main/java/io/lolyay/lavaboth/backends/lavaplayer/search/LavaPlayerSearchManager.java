package io.lolyay.lavaboth.backends.lavaplayer.search;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.search.Search;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class LavaPlayerSearchManager extends AbstractSearchManager {
    private final ArrayList<AbstractSearcher> searchers = new ArrayList<>();

    public LavaPlayerSearchManager(LavaPlayerPlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public CompletableFuture<Search> Internalsearch(String query, RequestorData requestorData) {
        CompletableFuture<Search> future = new CompletableFuture<>();
        submit(() -> trySearcherAtIndex(query, requestorData, 0, future),"Search-Slave");
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

        if(!searchers.get(index).canSearch(query)) {
            trySearcherAtIndex(query, requestorData, index + 1, mainFuture);
        }
        // BASE CASE: If we've run out of searchers, the search has failed.
        if (index >= searchers.size()) {
            mainFuture.complete(Search.wasNotFound(Search.SearchResult.ERROR("No searcher found a result for: " + query), "LavaPlayer", query));
            return; // Stop the recursion.
        }

        AbstractSearcher currentSearcher = searchers.get(index);

        // Perform the search with this single searcher. This returns a future immediately.
        CompletableFuture<Search> singleSearchFuture = searchWith(new LavaQuery(requestorData, query, currentSearcher));

        singleSearchFuture.whenComplete((result, exception) -> {
            if (exception == null && result != null && result.result().isSuccess()) {
                mainFuture.complete(result);
            } else {
                trySearcherAtIndex(query, requestorData, index + 1, mainFuture);
            }
        });
    }

    /**
     * Performs a search with a single searcher and returns a future for its result.
     */
    private CompletableFuture<Search> searchWith(LavaQuery lavaQuery) {
        CompletableFuture<Search> future = new CompletableFuture<>();
        ((LavaPlayerPlayerManager) getPlayerManager()).getAudioPlayerManager()
                .loadItem(
                        lavaQuery.getSource().getPrefix() + lavaQuery.getQuery(),
                        new LavaResultHandler(future, ((LavaPlayerPlayerManager) getPlayerManager()), lavaQuery)
                );
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


    public static class LavaQuery {
        private final RequestorData requestorData;
        private final String query;
        private final AbstractSearcher source;
        public LavaQuery(RequestorData requestorData, String query, AbstractSearcher source) {
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
