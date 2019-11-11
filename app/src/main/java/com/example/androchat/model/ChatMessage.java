package com.example.androchat.model;


public class ChatMessage {

    private String message;
    private String user_id;
    private String profile_image;
    private String name;
    private long currentTime;

    public ChatMessage(String message, String user_id, long currentTime, String profile_image, String name) {
        this.message = message;
        this.user_id = user_id;
        this.currentTime = currentTime;
        this.profile_image = profile_image;
        this.name = name;
    }
    public ChatMessage(String message,long currentTime,String user_id){
        this.message = message;
        this.user_id = user_id;
    }

    public ChatMessage() {

    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setDbMessage(String message, long currentTime, String user_id){
        this.message = message;
        this.user_id = user_id;
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", user_id='" + user_id + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
