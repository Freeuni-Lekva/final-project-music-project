package org.freeuni.musicforum.filter;


import org.freeuni.musicforum.model.SearchRequest;

public class scopeFilter implements Filter{

    private String username;

    public scopeFilter(String username){
        this.username = username;
    }

    @Override
    public boolean doFilter(SearchRequest request) {
        // implementation using getFriendshipStatus
        return true;
    }
}
