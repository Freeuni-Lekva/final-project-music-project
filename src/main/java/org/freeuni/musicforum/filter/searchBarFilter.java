package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

public class searchBarFilter implements Filter{

    private String key;

    public searchBarFilter(String key){
        this.key = key;
    }
    @Override
    public boolean doFilter(SearchRequest request) {

        boolean result = false;
        String albumName = request.getAlbumName();
        String artistName = request.getArtistName();
        String username = request.getUsername();

        if(albumName!=null){
            result = albumName.contains(key);
        }

        if(artistName!=null){
            if(!result){
                result = artistName.contains(key);
            }
        }

        if(username!=null){
            if(!result){
                result = username.contains(key);
            }
        }

        return result;
    }
}
