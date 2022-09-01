package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

import java.util.ArrayList;
import java.util.List;

public class filterManager implements Filter{

    private List<Filter> filters;

    public filterManager() {
        filters = new ArrayList<>();
    }

    public void add(Filter f) {
        filters.add(f);
    }


    @Override
    public boolean doFilter(SearchRequest request) {
        for (Filter f : filters) {
            if (!f.doFilter(request)) {
                return false;
            }
        }
        return true;
    }

}
