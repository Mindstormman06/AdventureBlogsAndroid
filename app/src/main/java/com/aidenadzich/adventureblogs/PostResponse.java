package com.aidenadzich.adventureblogs;

import com.aidenadzich.adventureblogs.models.Post;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponse {
    @SerializedName("success")
    private String status;

    @SerializedName("posts")
    private List<Post> data;

    public String getStatus() {
        return status;
    }

    public List<Post> getData() {
        return data;
    }
}
