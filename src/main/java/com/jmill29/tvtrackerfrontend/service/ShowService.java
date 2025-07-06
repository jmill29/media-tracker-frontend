package com.jmill29.tvtrackerfrontend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jmill29.tvtrackerfrontend.dto.ShowResponse;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;
import com.jmill29.tvtrackerfrontend.util.LocalDateTimeAdapter;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for retrieving and displaying TV show data.
 */
public class ShowService {

    // Endpoint to fetch list of TV shows
    private static final String SHOWS_URL = "http://localhost:8080/api/shows";

    // Gson instance configured with adapter for serializing/deserializing LocalDateTime
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    /**
     * Fetches all TV shows from the backend and displays them in the console.
     * Requires valid credentials to access the protected endpoint.
     *
     * @param username the username for Basic Auth
     * @param password the password for Basic Auth
     */
    public static void fetchAndDisplayShows(String username, String password) {
        try {
            // Send an authenticated GET request to fetch shows
            HttpResponse<String> response = HttpRequestUtil.sendGet(SHOWS_URL, username, password);

            if (response.statusCode() == 200) {
                // Deserialize the JSON response into a list of ShowResponse objects
                Type listType = new TypeToken<List<ShowResponse>>() {}.getType();
                List<ShowResponse> shows = gson.fromJson(response.body(), listType);

                // Display each show in a readable format
                System.out.println("\n=== TV Shows ===");
                for (ShowResponse show : shows) {
                    System.out.println("\n" + show.getId() + ".");
                    System.out.printf("🔹 %s (%d)\n", show.getName(), show.getReleaseYear());
                    System.out.println("📄 " + show.getDescription());
                    System.out.println("🖼️ Image URL: " + show.getImageUrl());
                    System.out.println("📺 Episodes: " + show.getNumEpisodes());
                }
                System.out.println();
            } else if (response.statusCode() == 404) {
                // No shows found
                System.out.println("\nℹ️ No TV shows found (404 returned from server).\n");
            } else {
                // Other server-side failure
                System.out.println("\n❌ Failed to fetch shows. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            // Catch networking or deserialization errors
            System.out.println("\n❌ An error occurred while fetching shows: " + e.getMessage());
        }
    }

}
