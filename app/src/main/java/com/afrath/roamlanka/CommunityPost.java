package com.afrath.roamlanka;

// Model class used to store community question data Each object represents one community post
public class CommunityPost {

    private String id;  // Unique ID of the question
    private String username;   // Nickname of the user who posted the question
    private String userId;    // Unique ID of the user who posted the question
    private String question; // Question text entered by the user
    private Long time; // Time when the question was posted

    public CommunityPost() {}  // Required by Firebase when converting database data into objects


    // Constructor used to create a CommunityPost object and initialize all fields
    public CommunityPost(String id, String username, String userId, String question, Long time) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.question = question;
        this.time = time;
    }

    public String getId() { return id; }  // Returns the question ID
    public String getUsername() { return username; }
    public String getUserId() { return userId; }  // Returns the user ID of the question owner
    public String getQuestion() { return question; }  // Returns the question text
    public Long getTime() { return time; }
}