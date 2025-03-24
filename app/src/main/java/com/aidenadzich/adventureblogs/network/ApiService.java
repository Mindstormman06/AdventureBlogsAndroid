package com.aidenadzich.adventureblogs.network;

import com.aidenadzich.adventureblogs.PostResponse;
import com.aidenadzich.adventureblogs.models.LoginRequest;
import com.aidenadzich.adventureblogs.models.LoginResponse;
import com.aidenadzich.adventureblogs.models.Post;

import retrofit2.http.Body;
import retrofit2.http.GET;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @GET("get_posts.php")
    Call<PostResponse> getPosts();
}
