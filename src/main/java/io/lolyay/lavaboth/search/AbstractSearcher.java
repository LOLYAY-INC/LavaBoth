package io.lolyay.lavaboth.search;


import io.lolyay.lavaboth.backends.common.AbstractSearchManager;

public abstract class AbstractSearcher {
    private final AbstractSearchManager searchManager;

    public AbstractSearcher(AbstractSearchManager searchManager) {
        this.searchManager = searchManager;
    }

    protected AbstractSearchManager getGuildMusicManager() {
        return searchManager;
    }


    public abstract boolean canSearch(String query);

    public abstract String getSourceName();

    public abstract String getPrefix(); // eg. "ytsearch:"



}
