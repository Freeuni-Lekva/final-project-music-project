package org.freeuni.musicforum.model;

import java.util.Date;
import java.util.HashMap;

public class User {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String username;
    private Password password;
    private String profileImageBase64;
    private String filename;
    private Badge badge;
    private HashMap<String, FriendshipStatus> friends;
    public User(String firstName,
                String lastName,
                Date birthDate,
                Gender gender,
                String username,
                Password password,
                Badge badge) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.badge = badge;
        friends = new HashMap<>();

    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Badge getBadge() {
        return badge;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }

    public HashMap<String, FriendshipStatus> getFriends() {
        return friends;
    }



    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setProfileImageBase64(String profileImageBase64){
        this.profileImageBase64 = profileImageBase64;
    }
}
