package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.SearchRequest;

public class SearchBarFilter implements Filter{

    private String key;

    public SearchBarFilter(String key){
        this.key = key.toLowerCase();
    }
    @Override
    public boolean doFilter(SearchRequest request) {

        boolean result = false;
        String albumName = request.getAlbumName();
        String artistName = request.getArtistName();
        String username = request.getUsername();

        if(albumName!=null){
            result = albumName.toLowerCase().contains(key);
        }

        if(artistName!=null){
            if(!result){
                result = artistName.toLowerCase().contains(key);
            }
        }

        if(username!=null){
            if(!result){
                result = username.toLowerCase().contains(key);
            }
        }

        return result;
    }
}
