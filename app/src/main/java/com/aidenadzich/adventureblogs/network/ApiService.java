package com.aidenadzich.adventureblogs.network;

import com.aidenadzich.adventureblogs.PostResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiService {

    @GET("get_posts.php")
    Call<PostResponse> getPosts();
}
