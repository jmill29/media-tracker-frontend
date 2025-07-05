package com.jmill29.tvtrackerfrontend.service;

import com.google.gson.Gson;
import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;

import java.net.http.HttpResponse;

/**
 * Service class for handling user-related operations, such as registration.
 */
public class UserService {

    // Endpoint for registering new users
    private static final String REGISTER_URL = "http://localhost:8080/auth/register";

    // Gson instance for converting Java objects to JSON
    private static final Gson gson = new Gson();

    /**
     * Sends a POST request to register a new user.
     *
     * @param userRequest a {@link UserRequest} object containing the user's registration data
     * @return true if registration was successful, false otherwise
     */
    public static boolean registerUser(UserRequest userRequest) {
        try {
            // Convert the user request to a JSON string
            String jsonBody = gson.toJson(userRequest);

            // Send the POST request (no auth required for registration)
            HttpResponse<String> response = HttpRequestUtil.sendPost(REGISTER_URL, jsonBody, "", "");

            // Check for successful response codes (200 OK or 201 Created)
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("\n✅ Registration successful! You may now log in.\n");
                return true;
            } else {
                // Registration failed (e.g., validation error, username taken, etc.)
                System.out.println("\n❌ Registration failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            // Catch and log any unexpected errors
            System.out.println("\n❌ An error occurred while registering: " + e.getMessage() + "\n");
        }

        return false; // Default to false if registration failed or exception occurred
    }

}
