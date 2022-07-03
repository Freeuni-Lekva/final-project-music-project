package org.freeuni.musicforum.filter;

import org.freeuni.musicforum.model.User;



public class userFilter implements Filter {

    private String username;

    public userFilter(String username){
        this.username = username;
    }


    @Override
    public boolean doFilter(Object o) {
        User u = (User) o;
        return username.contains(u.username());
    }
}
