package com.example.pizzeria.console.http;

public class ApiResponse {

    private final int statusCode;
    private final String body;
    private final boolean success;

    public ApiResponse(int statusCode, String body) {

        this.statusCode = statusCode;
        this.body = body;
        this.success = statusCode >= 200 && statusCode < 300;

    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public boolean isSuccess() {
        return success;
    }
}
