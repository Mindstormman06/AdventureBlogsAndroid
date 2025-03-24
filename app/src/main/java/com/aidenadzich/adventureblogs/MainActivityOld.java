package com.aidenadzich.adventureblogs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivityOld extends AppCompatActivity {

    private EditText usernameInput;

    private EditText passwordInput;
    private Button loginButton;
    private TextView resultText;
    private final String API_URL = "http://adventure-blog.ddns.net/adventure-blogs-api/auth.php"; // Use 10.0.2.2 for localhost in Android Emulator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        resultText = findViewById(R.id.resultText);

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
                String response = ApiHelperOld.postRequest(API_URL, json.toString());


                runOnUiThread(() -> resultText.setText(response));





            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}