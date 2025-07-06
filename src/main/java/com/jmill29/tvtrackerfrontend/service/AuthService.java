package com.jmill29.tvtrackerfrontend.service;

import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;
import java.net.http.HttpResponse;

/**
 * Service class responsible for handling user authentication logic.
 */
public class AuthService {

    // Endpoint used to verify user credentials
    private static final String VALIDATE_CREDENTIALS_URL = "http://localhost:8080/api/watch-history";

    /**
     * Attempts to log in using the provided username and password by sending
     * an authenticated GET request to the backend.
     *
     * @param username the username to log in with
     * @param password the password to log in with
     * @return true if login was successful or user not found (404), false otherwise
     */
    public static boolean login(String username, String password) {
        try {
            // Send GET request to protected endpoint to validate credentials
            HttpResponse<String> response = HttpRequestUtil.sendGet(VALIDATE_CREDENTIALS_URL, username, password);
            int statusCode = response.statusCode();

            // Consider 200 OK and 404 Not Found as successful logins for our purposes
            if (statusCode == 200 || statusCode == 404) {
                return true;
            }
        } catch (Exception e) {
            // Catch network or server errors
            System.out.println("\n❌ An error occurred during login: " + e.getMessage());
        }

        return false;
    }
}
