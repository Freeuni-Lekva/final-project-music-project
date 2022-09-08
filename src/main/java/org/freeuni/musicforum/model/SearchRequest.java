package org.freeuni.musicforum.model;

import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;

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
            throw new IllegalArgumentException();
        }

    }


    public Date getDate(){
        if(user!=null) return null;
        if(album!=null) return album.uploadDate();
        if(review!=null) return review.getUploadDate();
        return null;
    }

    public String getAlbumName(){
        if(user!=null) return null;
        if(album!=null) return album.albumName();
        if(review!=null) return ServiceFactory.getAlbumService().getAlbum(review.getAlbumId()).albumName();
        return null;
    }

    public String getUsername() {
        if(user!=null) return user.getUsername();
        if(album!=null) return album.username();
        if(review!=null) return review.getAuthorUsername();
        return null;
    }

    public String getArtistName(){
        if(user!=null) return null;
        if(album!=null) return album.artistName();
        if(review!=null) return ServiceFactory.getAlbumService().getAlbum(review.getAlbumId()).artistName();
        return null;
    }

    public FriendshipStatus getFriendshipStatus(String username){
        UserService us = ServiceFactory.getUserService();
        if(user!=null) return us.getFriendshipStatus(username, user.getUsername());
        if(album!=null) return us.getFriendshipStatus(username, album.username());
        if(review!=null) return us.getFriendshipStatus(username, review.getAuthorUsername());
        return null;
    }

    public Status getStatus(){
        if(user!=null) return user.getStatus();
        return null;
    }


}
