package com.afrath.roamlanka;


// Model class used to store comment information
public class CommentModel {

    private String id;  // Unique ID of the comment
    private String text;  // Comment text written by the user
    private String user;  // Name (nickname) of the user who posted the comment
    private String userId;   // Unique ID of the user who posted the comment
    private Long time; // Time when the comment was created

    public CommentModel() {}      // Required by Firebase when converting database data into objects


    // Constructor used to create a CommentModel object and initialize all fields
    public CommentModel(String id, String text, String user, String userId, Long time) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.userId = userId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public Long getTime() {
        return time;
    }
}