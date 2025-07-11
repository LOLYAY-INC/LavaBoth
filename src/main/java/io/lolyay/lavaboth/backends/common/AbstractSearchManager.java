package io.lolyay.lavaboth.backends.common;

import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.search.AbstractSearcher;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.search.searchers.DefaultSearcher;
import io.lolyay.lavaboth.search.searchers.HttpSearcher;
import io.lolyay.lavaboth.search.searchers.YoutubeMusicSearcher;
import io.lolyay.lavaboth.search.searchers.YoutubeSearcher;

import java.util.function.Consumer;

public abstract class AbstractSearchManager {
    private final AbstractPlayerManager playerManager;
    public AbstractSearchManager(AbstractPlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public AbstractPlayerManager getPlayerManager() {
        return playerManager;
    }

    public abstract void search(String query, Consumer<Search> callback, RequestorData requestorData);
    public void search(String query, Consumer<Search> callback) {
        search(query, callback, RequestorData.anonymous());
    }

    public abstract void registerSearcher(AbstractSearcher searcher);
    public abstract void unregisterSearcher(AbstractSearcher searcher);
    public void registerDefaultSearchers(){
        registerSearcher(new HttpSearcher(this));
        registerSearcher(new YoutubeMusicSearcher(this));
        registerSearcher(new YoutubeSearcher(this));
        registerSearcher(new DefaultSearcher(this));
    }
}
