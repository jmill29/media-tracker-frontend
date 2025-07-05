package com.jmill29.tvtrackerfrontend.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

/**
 * Utility class for sending HTTP requests to the backend API.
 */
public class HttpRequestUtil {

    // Shared HTTP client with a timeout of 10 seconds
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * Generates a Basic Authorization header from a username and password.
     *
     * @param username the username
     * @param password the password
     * @return the Basic Auth header value
     */
    private static String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    /**
     * Checks whether valid login info is present.
     *
     * @param username the username
     * @param password the password
     * @return true if both username and password are non-null and non-blank
     */
    public static boolean loginInfoNotNull(String username, String password) {
        return username != null && !username.isBlank()
                && password != null && !password.isBlank();
    }

    /**
     * Sends a GET request to the specified URL with optional Basic Auth.
     *
     * @param url the endpoint to send the GET request to
     * @param username optional username for Basic Auth
     * @param password optional password for Basic Auth
     * @return the HTTP response
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> sendGet(String url, String username, String password) throws Exception {
        boolean includeAuthHeader = loginInfoNotNull(username, password);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if (includeAuthHeader) {
            builder.header("Authorization", getBasicAuthHeader(username, password));
        }

        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Sends a POST request with JSON body to the specified URL with optional Basic Auth.
     *
     * @param url the endpoint to send the POST request to
     * @param jsonBody the JSON payload
     * @param username optional username for Basic Auth
     * @param password optional password for Basic Auth
     * @return the HTTP response
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> sendPost(String url, String jsonBody, String username, String password) throws Exception {
        boolean includeAuthHeader = loginInfoNotNull(username, password);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        if (includeAuthHeader) {
            builder.header("Authorization", getBasicAuthHeader(username, password));
        }

        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Sends a PUT request with JSON body and Basic Auth to the specified URL.
     *
     * @param url the endpoint to send the PUT request to
     * @param jsonBody the JSON payload
     * @param username the username for Basic Auth
     * @param password the password for Basic Auth
     * @return the HTTP response
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> sendPut(String url, String jsonBody, String username, String password) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthHeader(username, password))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Sends a DELETE request with Basic Auth to the specified URL.
     *
     * @param url the endpoint to send the DELETE request to
     * @param username the username for Basic Auth
     * @param password the password for Basic Auth
     * @return the HTTP response
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> sendDelete(String url, String username, String password) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", getBasicAuthHeader(username, password))
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
