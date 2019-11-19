package com.example.androchat.model;

public class User {

    private String name;
    private String profile_image;
    private String user_id;
    private String username;

    public User(String name, String profile_image, String user_id) {
        this.name = name;
        this.profile_image = profile_image;
        this.user_id = user_id;
    }
    public User(String name, String profile_image, String user_id,String username) {
        this.name = name;
        this.profile_image = profile_image;
        this.user_id = user_id;
        this.username = username;
    }

    public User() {

    }
    public String getUser_id() {
        return user_id;
    }

    private void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getProfile_image() {
        return profile_image;
    }

    private void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setAllValues(String user_id, String name, String profile_image){
        setUser_id(user_id);
        setProfile_image(profile_image);
        setName(name);
    }
    public void setNUPValues(String name,String username, String profile_image){
        setUsername(username);
        setName(name);
        setProfile_image(profile_image);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }
}
