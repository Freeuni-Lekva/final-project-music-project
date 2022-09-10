package org.freeuni.musicforum.filter;


import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.SearchRequest;

public class ScopeFilter implements Filter{

    private String username;

    public ScopeFilter(String username){
        this.username = username;
    }

    @Override
    public boolean doFilter(SearchRequest request) {
        FriendshipStatus fs = request.getFriendshipStatus(username);
        return fs!=null&&fs.equals(FriendshipStatus.FRIENDS);
    }
}
