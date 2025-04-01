package com.aidenadzich.adventureblogs.network;

import com.aidenadzich.adventureblogs.PostResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @GET("get_posts.php")
    Call<PostResponse> getPosts();

    @Multipart
    @POST("create_post.php")
    Call<Void> uploadPost(
            @Header("Authorization") String token,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("tags") RequestBody tags,
            @Part("location") RequestBody location,
            @Part MultipartBody.Part file
    );
}
