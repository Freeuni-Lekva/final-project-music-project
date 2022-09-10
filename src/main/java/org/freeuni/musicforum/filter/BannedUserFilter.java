package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.model.Status;

public class BannedUserFilter implements Filter{

    @Override
    public boolean doFilter(SearchRequest request) {
        if(request.getStatus()==null) return true;
        return request.getStatus().equals(Status.ACTIVE);
    }
}
