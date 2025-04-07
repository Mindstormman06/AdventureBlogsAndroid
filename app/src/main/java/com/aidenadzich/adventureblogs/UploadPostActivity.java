package com.aidenadzich.adventureblogs;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.aidenadzich.adventureblogs.network.ApiClient;
import com.aidenadzich.adventureblogs.network.ApiService;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class UploadPostActivity extends AppCompatActivity {
    private EditText titleInput, contentInput, tagsInput, locationInput;
    private ImageView mapView;
    private Button uploadButton;
    private Button fileButton;

    private String jwtToken; // Define jwtToken
    private String filePath; // Define filePath

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        tagsInput = findViewById(R.id.tagsInput);
        locationInput = findViewById(R.id.locationInput);
        mapView = findViewById(R.id.mapView);
        uploadButton = findViewById(R.id.uploadButton);
        fileButton = findViewById(R.id.fileButton);
        jwtToken = getIntent().getStringExtra("jwt_token");
        uploadButton.setOnClickListener(view -> uploadPost());
        fileButton.setOnClickListener(view -> openFilePicker());
    }

    private void uploadPost() {
        if (filePath == null || filePath.isEmpty()) {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();
        String tags = tagsInput.getText().toString();
        String location = locationInput.getText().toString();

        File file = new File(filePath);

        // Ensure token is set
        if (jwtToken == null || jwtToken.isEmpty()) {
            Log.e("UPLOAD_ERROR", "JWT Token is missing!");
            Toast.makeText(this, "JWT Token is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody titlePart = RequestBody.create(MultipartBody.FORM, title);
        RequestBody contentPart = RequestBody.create(MultipartBody.FORM, content);
        RequestBody tagsPart = RequestBody.create(MultipartBody.FORM, tags);
        RequestBody locationPart = RequestBody.create(MultipartBody.FORM, location);
        RequestBody filePart = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", file.getName(), filePart);

        // Log the JWT token and request data
        Log.d("UPLOAD_DEBUG", "JWT Token: " + jwtToken);
        Log.d("UPLOAD_DEBUG", "Sending Request with data:");
        Log.d("UPLOAD_DEBUG", "Title: " + title);
        Log.d("UPLOAD_DEBUG", "Content: " + content);
        Log.d("UPLOAD_DEBUG", "Tags: " + tags);
        Log.d("UPLOAD_DEBUG", "Location: " + location);
        Log.d("UPLOAD_DEBUG", "File: " + file.getName());

        // Create a MultipartBody with all the parts for the file upload and fields
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("tags", tags)
                .addFormDataPart("location", location)
                .addFormDataPart("files[]", file.getName(), filePart)
                .build();

        // Build the request with OkHttpClient
        Request request = new Request.Builder()
                .url("https://adventure-blog.ddns.net/adventure-blogs-api/create_post.php") // Replace with actual endpoint
                .addHeader("Authorization", "Bearer " + jwtToken) // Add the Authorization header
                .post(requestBody) // Set the request body
                .build();

        Log.d("UPLOAD_DEBUG", "Request: " + request.toString());
        Log.d("UPLOAD_DEBUG", "Request body: " + requestBody.toString()); // Log the request body

        // Create OkHttpClient instance

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();


        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Log.d("UPLOAD_DEBUG", "Response: " + response);
                    Log.d("UPLOAD_DEBUG", "Response Body: " + response.body());
                    runOnUiThread(() -> {
                        Toast.makeText(UploadPostActivity.this, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    Log.e("UPLOAD_ERROR", "Upload failed: " + response.message());
                    runOnUiThread(() -> {
                        Toast.makeText(UploadPostActivity.this, "Upload failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure, typically network issues or timeouts
                Log.e("UPLOAD_ERROR", "Upload failed: " + e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(UploadPostActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }



    private static final int PICK_FILE_REQUEST = 1;

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            filePath = FileUtils.getPathFromUri(this, fileUri);

            Log.d("DEBUG", "File URI: " + fileUri.toString());
            Log.d("DEBUG", "File Path: " + filePath);

            if (filePath == null) {
                Toast.makeText(this, "Failed to get file path", Toast.LENGTH_SHORT).show();
            }
        }
    }


}