package io.lolyay.lavaboth.backends.common;

import io.lolyay.lavaboth.NamedThreadFactory;
import io.lolyay.lavaboth.ThreadNamingExecutorService;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.search.searchers.DefaultSearcher;
import io.lolyay.lavaboth.search.searchers.HttpSearcher;
import io.lolyay.lavaboth.search.searchers.YoutubeMusicSearcher;
import io.lolyay.lavaboth.search.searchers.YoutubeSearcher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public abstract class AbstractSearchManager {
    private final AbstractPlayerManager playerManager;
    private ThreadNamingExecutorService executorService = new ThreadNamingExecutorService(Executors.newCachedThreadPool());
    public AbstractSearchManager(AbstractPlayerManager playerManager) {
        this.playerManager = playerManager;

    }

    public AbstractPlayerManager getPlayerManager() {
        return playerManager;
    }
    protected abstract CompletableFuture<Search> Internalsearch(String query, RequestorData requestorData);

    public CompletableFuture<Search> search(String query, RequestorData requestorData){
        return Internalsearch(query, requestorData);
    }
    public CompletableFuture<Search> search(String query) {
        return search(query, RequestorData.anonymous());
    }

    public abstract void registerSearcher(AbstractSearcher searcher);
    public abstract void unregisterSearcher(AbstractSearcher searcher);
    public void registerDefaultSearchers(){
        registerSearcher(new HttpSearcher(this));
        registerSearcher(new YoutubeMusicSearcher(this));
        registerSearcher(new YoutubeSearcher(this));
        registerSearcher(new DefaultSearcher(this));
    }

    protected void submit(Runnable runnable, String threadNamePrefix) {
        executorService.submit(runnable, threadNamePrefix);
    }

}
