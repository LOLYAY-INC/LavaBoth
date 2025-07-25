package io.lolyay.lavaboth.search.searchers;

import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.search.AbstractSearcher;

public class YoutubeMusicSearcher extends AbstractSearcher {
    public YoutubeMusicSearcher(AbstractSearchManager guildMusicManager) {
        super(guildMusicManager);
    }

    @Override
    public boolean canSearch(String query) {
        return !(query.contains("https://") && !query.contains("youtu"));
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
