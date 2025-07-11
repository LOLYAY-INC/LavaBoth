package io.lolyay.lavaboth.backends.lavalinkclient.search;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.utils.Logger;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LavaLinkSearchManager extends AbstractSearchManager {
    private final ArrayList<AbstractSearcher> searchers = new ArrayList<>();

    public LavaLinkSearchManager(LavaLinkPlayerManager playerManager) {
        super(playerManager);
    }
    @Override
    public void search(String query, Consumer<Search> callback, RequestorData requestorData) {
        new Thread(() -> {
            search(query, callback, requestorData, 0);
        }).start();
    }

    private void search(String query, Consumer<Search> callback, RequestorData requestorData,int iter) {
        AbstractSearcher searcher = searchers.get(iter);
        if (searcher.canSearch(searcher.getPrefix() + query)) {
            try {
                search(new LinkQuery(requestorData, query, searcher, search -> {
                    if (search.result().isSuccess()) {
                        callback.accept(search);
                    } else if (iter < searchers.size() - 1) {
                        search(query, callback, requestorData, iter + 1);
                    } else {
                        callback.accept(search);
                    }
                }));
            } catch (Exception e) {
                Logger.err(e.getMessage());
                e.printStackTrace();
            }
        }
        else if (iter < searchers.size() - 1) {
            search(query, callback, requestorData, iter + 1);
        }
        else {
            callback.accept(Search.wasNotFound(Search.SearchResult.ERROR("No searchers found."), "LavaLink", query));
        }
    }

    private void search(LinkQuery lavaQuery) {
        ((LavaLinkPlayerManager) getPlayerManager()).getJLavalinkClient().loadTracks(lavaQuery.getSource().getPrefix() + lavaQuery.getQuery()).thenAccept(
                new LinkResultHandler(lavaQuery.getCallback(),(LavaLinkPlayerManager) getPlayerManager(), lavaQuery)
        );
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
        private final Consumer<Search> callback;
        public LinkQuery(RequestorData requestorData, String query, AbstractSearcher source, Consumer<Search> callback) {
            this.requestorData = requestorData;
            this.query = query;
            this.source = source;
            this.callback = callback;
        }

        public RequestorData getRequestorData() {
            return requestorData;
        }

        public String getQuery() {
            return query;
        }

        public Consumer<Search> getCallback() {
            return callback;
        }

        public AbstractSearcher getSource() {
            return source;
        }
    }
}
