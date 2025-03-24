package com.aidenadzich.adventureblogs;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aidenadzich.adventureblogs.adapters.PostAdapter;
import com.aidenadzich.adventureblogs.models.Post;
import com.aidenadzich.adventureblogs.network.ApiClient;
import com.aidenadzich.adventureblogs.network.ApiService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private PostAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchPosts();
    }

    private void fetchPosts() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PostResponse> call = apiService.getPosts();

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", new Gson().toJson(response.body())); // Log full response

                    List<Post> posts = response.body().getData();
                    if (posts != null) {
                        adapter = new PostAdapter(MainActivity.this, posts);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("API_ERROR", "Data is null");
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or body is null");
                }
            }


            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch posts: " + t.getMessage());
            }
        });
    }
}
