package com.example.pizzeria.console.utils;

import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String extract(ApiResponse response) {

        try {

            ErrorResponse error = mapper.readValue(response.getBody(), ErrorResponse.class);
            return error.getMessage();
        } catch (Exception e) {
            return response.getBody();
        }
    }

}