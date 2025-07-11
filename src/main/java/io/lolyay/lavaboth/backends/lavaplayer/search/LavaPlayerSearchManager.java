package io.lolyay.lavaboth.backends.lavaplayer.search;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.search.Search;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LavaPlayerSearchManager extends AbstractSearchManager {
    private final ArrayList<AbstractSearcher> searchers = new ArrayList<>();

    public LavaPlayerSearchManager(LavaPlayerPlayerManager playerManager) {
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
            search(new LavaQuery(requestorData, query, searcher, search ->{
                if (search.result().isSuccess()) {
                    callback.accept(search);
                } else if (iter < searchers.size() - 1) {
                    search(query, callback, requestorData, iter + 1);
                } else {
                    callback.accept(search);
                }
            }));
        }
        else if (iter < searchers.size() - 1) {
            search(query, callback, requestorData, iter + 1);
        }
        else {
            callback.accept(Search.wasNotFound(Search.SearchResult.ERROR("No searchers found."), "LavaPlayer", query));
        }
    }

    private void search(LavaQuery lavaQuery) {
        ((LavaPlayerPlayerManager) getPlayerManager()).getAudioPlayerManager()
                .loadItem(
                        lavaQuery.getSource().getPrefix() + lavaQuery.getQuery(),
                        new LavaResultHandler(lavaQuery.getCallback(), ((LavaPlayerPlayerManager) getPlayerManager()), lavaQuery)
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


    public static class LavaQuery {
        private final RequestorData requestorData;
        private final String query;
        private final AbstractSearcher source;
        private final Consumer<Search> callback;
        public LavaQuery(RequestorData requestorData, String query, AbstractSearcher source, Consumer<Search> callback) {
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
