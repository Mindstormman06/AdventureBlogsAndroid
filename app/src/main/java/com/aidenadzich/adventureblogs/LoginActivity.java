package com.aidenadzich.adventureblogs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aidenadzich.adventureblogs.network.ApiClient;
import com.aidenadzich.adventureblogs.network.ApiService;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView resultText;
    private final String API_URL = "https://adventure-blog.ddns.net/adventure-blogs-api/auth.php"; // Use 10.0.2.2 for localhost in Android Emulator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Prepare JSON request
                JSONObject json = new JSONObject();
                json.put("username", usernameInput.getText().toString());
                json.put("password", passwordInput.getText().toString());

                // Send request
                String response = ApiClient.postRequest(API_URL, json.toString());

                runOnUiThread(() -> {
                    if (response != null && response.contains("success")) {
                        // Navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        resultText.setText("Login failed. Please try again.");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}