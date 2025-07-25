package io.lolyay.lavaboth.search.searchers;


import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.search.AbstractSearcher;

public class YoutubeSearcher extends AbstractSearcher {
    public YoutubeSearcher(AbstractSearchManager guildMusicManager) {
        super(guildMusicManager);
    }

    @Override
    public boolean canSearch(String query) {
        return true;
    }

    @Override
    public String getPrefix() {
        return "ytsearch:";
    }

    @Override
    public String getSourceName() {
        return "Youtube";
    }

}
