package com.creapple.movieapp;

public class CommentInfo {

    String id;
    String time;
    float rating;
    String comment;

    public CommentInfo(String id, float rating,String time, String comment) {

        this.id = id;
        this.time = time;
       this.rating = rating;
        this.comment = comment;
    }


}
