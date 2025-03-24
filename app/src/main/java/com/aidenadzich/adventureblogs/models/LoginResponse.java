package com.aidenadzich.adventureblogs.models;

public class LoginResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}