package io.lolyay.lavaboth.search.searchers;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.search.AbstractSearcher;

public class YoutubeMusicSearcher extends AbstractSearcher {
    public YoutubeMusicSearcher(AbstractSearchManager guildMusicManager) {
        super(guildMusicManager);
    }

    @Override
    public boolean canSearch(String query) {
        return query.startsWith("ytmsearch:") ||
                query.contains("music.youtube.com/watch");
    }

    @Override
    public String getPrefix() {
        return "ytmsearch:";
    }

    @Override
    public String getSourceName() {
        return "Youtube Music";
    }

}
