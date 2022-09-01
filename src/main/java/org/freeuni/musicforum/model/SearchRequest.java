package org.freeuni.musicforum.model;

import java.util.Date;

public class SearchRequest {


    private User user;

    private Review review;

    private Album album;

    public SearchRequest(Object o){

        Class objClass = o.getClass();

        if(objClass==User.class){
            user = (User) o;
        } else if (objClass==Review.class){
            review = (Review) o;
        } else if(objClass==Album.class){
            album = (Album) o;
        } else{
            // exception
        }

    }

    public String getUsername() {
        /*
        if(user!=null) return user.username();
        if(album!=null) return album.username();
        if(review!=null) return review.authUsername();
        **/
        return null;
    }


    public Date getDate(){
        /*
        if(user!=null) return null;
        //if(album!=null) return album.date;
        //if(review!=null) return review.date;
        **/
        return null;
    }

    public String getAlbumName(){

        return null;
    }

    public String getArtistName(){

        return null;
    }


}
