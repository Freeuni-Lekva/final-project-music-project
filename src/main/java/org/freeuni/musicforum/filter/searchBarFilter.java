package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

public class searchBarFilter implements Filter{

    private String key;

    public searchBarFilter(String key){
        this.key = key;
    }
    @Override
    public boolean doFilter(SearchRequest request) {
        // implementation here using getUsername, getAlbumName, getArtistName
        return false;
    }
}
