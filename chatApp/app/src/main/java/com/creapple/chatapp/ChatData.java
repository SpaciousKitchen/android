package com.creapple.chatapp;

public class ChatData
{
    public String username;
    public String message;
    public ChatData(){


    }

    public ChatData(String username, String message) {
        this.username = username;
        this.message = message;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
