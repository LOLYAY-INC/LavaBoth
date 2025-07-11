package io.lolyay.lavaboth.search.searchers;


import io.lolyay.lavaboth.backends.common.AbstractSearchManager;
import io.lolyay.lavaboth.search.AbstractSearcher;

public class DefaultSearcher extends AbstractSearcher {

    public DefaultSearcher(AbstractSearchManager searchManager) {
        super(searchManager);
    }

    @Override
    public boolean canSearch(String query) {
        return query.contains(":");
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public String getSourceName() {
        return "Other Sources";
    }

}
