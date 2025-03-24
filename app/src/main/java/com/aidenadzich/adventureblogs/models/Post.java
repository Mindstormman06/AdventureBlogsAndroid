package com.aidenadzich.adventureblogs.models;

import java.util.List;

public class Post {
    private int id;
    private String title;
    private String content;
    private String image_path;
    private String username;
    private String created_at;
    private String profile_photo;

    private List<FileData> files; // List of images

    public List<FileData> getFiles() {
        return files;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImagePath() { return image_path; }
    public String getUsername() { return username; }
    public String getCreatedAt() { return created_at; }
    public String getProfilePhoto() { return profile_photo; }
}
