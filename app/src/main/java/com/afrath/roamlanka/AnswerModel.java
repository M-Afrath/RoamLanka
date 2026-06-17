package com.afrath.roamlanka;

// Model class used to store answer information
public class AnswerModel {

    private String id;
    private String userId;
    private String username;
    private String answer;
    private Long time;

    public AnswerModel() {}  // Required by Firebase when converting database data into objects

    // Constructor used to create an AnswerModel object and initialize all fields
    public AnswerModel(String id, String userId, String username, String answer, Long time) {

        this.id = id;
        this.userId = userId;
        this.username = username;
        this.answer = answer;
        this.time = time;
    }

    public String getId() { return id; } // Returns the answer ID
    public String getUserId() { return userId; } // Returns the user ID of the answer owner
    public String getUsername() { return username; } // Returns the username of the person who posted the answer
    public String getAnswer() { return answer; } // Returns the answer text
    public Long getTime() { return time; }  // Returns the time when the answer was posted
}