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
import com.aidenadzich.adventureblogs.models.LoginRequest;
import com.aidenadzich.adventureblogs.models.LoginResponse;
import com.aidenadzich.adventureblogs.models.Post;
import com.aidenadzich.adventureblogs.network.ApiClient;
import com.aidenadzich.adventureblogs.network.ApiService;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private EditText usernameInput;

    private EditText passwordInput;

    private Button loginButton;

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        resultText = findViewById(R.id.resultText);

        loginButton.setOnClickListener(view -> loginUser());


        fetchPosts();
    }

    private void loginUser() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(new LoginRequest(username, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultText.setText(response.body().getMessage());
                } else {
                    resultText.setText("Login failed");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                resultText.setText("Error: " + t.getMessage());
            }
        });
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
