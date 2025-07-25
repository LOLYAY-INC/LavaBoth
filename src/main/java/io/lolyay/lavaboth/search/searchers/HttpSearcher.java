package io.lolyay.lavaboth.search.searchers;


import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.search.AbstractSearcher;

public class HttpSearcher extends AbstractSearcher {
    public HttpSearcher(AbstractSearchManager searchManager) {
        super(searchManager);
    }

    @Override
    public boolean canSearch(String query) {
            return (query.startsWith("http://") || query.startsWith("https://") )&&
                !query.contains("spotify") &&
                !query.contains("soundcloud") &&
                !query.contains("tidal") &&
                !query.contains("deezer");
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public String getSourceName() {
        return "Internet";
    }

}
