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
    private Badge badge;
    private Status status;
    private HashMap<String, FriendshipStatus> friends;
    public User(String firstName,
                String lastName,
                Date birthDate,
                Gender gender,
                String username,
                Password password,
                Badge badge) {
        new User(firstName, lastName, birthDate, gender, null, username, password, badge);

    }
    public User(String firstName,
                String lastName,
                Date birthDate,
                Gender gender,
                String profileImageBase64,
                String username,
                Password password,
                Badge badge) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profileImageBase64 = profileImageBase64;
        this.username = username;
        this.password = password;
        this.badge = badge;
        this.status = Status.ACTIVE;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) { this.status = status; }

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

    public int getGenderIntValue(){
        if(gender==Gender.MAN){
            return 0;
        } else if(gender==Gender.WOMAN){
            return 1;
        } else if(gender==Gender.OTHER){
            return 2;
        }
        return -1;
    }

    public void setProfileImageBase64(String profileImageBase64){
        this.profileImageBase64 = profileImageBase64;
    }
}
