package com.afrath.roamlanka;


// It is used to store story information from Firestore
public class TravelStory {

    private String id;
    private String title;
    private String description;
    private String location;
    private String userId;
    private String nickname;
    private long timestamp;

    public TravelStory() {}  // Required by Firebase when converting data into objects


    // Constructor used to create a TravelStory object
    // and initialize all fields at once
    public TravelStory(String id, String title, String description,
                       String location, String userId, String nickname, long timestamp) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.userId = userId;
        this.nickname = nickname;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getUserId() { return userId; }  // Returns the user ID of the story owner
    public String getNickname() { return nickname; }
    public long getTimestamp() { return timestamp; }
}