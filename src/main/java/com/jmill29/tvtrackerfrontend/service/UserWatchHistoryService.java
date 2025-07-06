package com.jmill29.tvtrackerfrontend.service;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jmill29.tvtrackerfrontend.dto.UserWatchHistoryRequest;
import com.jmill29.tvtrackerfrontend.dto.UserWatchHistoryResponse;
import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;


/**
 * Service class for managing user watch history operations.
 * <p>
 * Provides methods to fetch, add, update, and delete TV show watch history for a user.<br>
 * Interacts with the backend API using HTTP requests and prints results to the console.
 * </p>
 */
public class UserWatchHistoryService {

    private static final String WATCH_HISTORY_URL = "http://localhost:8080/api/watch-history";
    private static final Gson gson = new Gson();

    /**
     * Fetches and displays the user's watch history from the backend API.
     *
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public static void fetchAndDisplayWatchHistory(String username, String password) {
        try {
            HttpResponse<String> response = HttpRequestUtil.sendGet(WATCH_HISTORY_URL, username, password);

            switch (response.statusCode()) {
                case 200 -> {
                    Type listType = new TypeToken<List<UserWatchHistoryResponse>>() {}.getType();
                    List<UserWatchHistoryResponse> history = gson.fromJson(response.body(), listType);
                    System.out.println("\n=== Your Watch History ===");
                    for (UserWatchHistoryResponse record : history) {
                        System.out.println("\n" + record.getShowId() + ".");
                        System.out.printf("📺 %s\n", record.getShowName());
                        System.out.println("📄 " + record.getDescription());
                        System.out.println("🖼️  Image URL: " + record.getImageUrl());
                        System.out.println("📌 Status: " + record.getStatus());
                    }
                    System.out.println();
                }
                case 404 -> System.out.println("\nℹ️ Your watch history is empty.\n");
                default -> System.out.println("\n❌ Failed to fetch watch history. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while fetching watch history: " + e.getMessage());
        }
    }

    /**
     * Adds a show to the user's watch history.
     *
     * @param showId   the ID of the show to add
     * @param status   the watch status (e.g., "CURRENTLY_WATCHING", "ALREADY_WATCHED")
     * @param username the username for authentication
     * @param password the password for authentication
     * @return true if the show was added successfully, false otherwise
     */
    public static boolean addToWatchHistory(int showId, String status, String username, String password) {
        UserWatchHistoryRequest request = new UserWatchHistoryRequest(showId, WatchStatus.fromDbValue(status));
        String jsonBody = new Gson().toJson(request);

        try {
            HttpResponse<String> response = HttpRequestUtil.sendPost(WATCH_HISTORY_URL, jsonBody, username, password);

            switch (response.statusCode()) {
                case 200, 201 -> {
                    return true;
                }
                case 404 -> System.out.println("\n⚠️ Show not found. Please check the Show ID and try again.\n");
                case 409 -> System.out.println("\n⚠️ This show is already in your watch history.\n");
                default -> System.out.println("\n❌ Failed to add show. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while adding to watch history: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates the watch status of a show in the user's watch history.
     *
     * @param showId    the ID of the show to update
     * @param newStatus the new watch status (e.g., "CURRENTLY_WATCHING", "ALREADY_WATCHED")
     * @param username  the username for authentication
     * @param password  the password for authentication
     * @return true if the status was updated successfully, false otherwise
     */
    public static boolean updateWatchStatus(int showId, String newStatus, String username, String password) {
        UserWatchHistoryRequest request = new UserWatchHistoryRequest(showId, WatchStatus.fromDbValue(newStatus));
        String jsonBody = new Gson().toJson(request);

        try {
            HttpResponse<String> response = HttpRequestUtil.sendPut(WATCH_HISTORY_URL, jsonBody, username, password);

            switch (response.statusCode()) {
                case 200 -> {
                    return true;
                }
                case 404 -> {
                    String responseBody = response.body().toLowerCase();
                    // Distinguish between not in watch history, show not found, or generic 404
                    if (responseBody.contains("watch history")) {
                        System.out.println("\n⚠️ This show is not in your watch history.\n");
                    } else if (responseBody.contains("show")) {
                        System.out.println("\n⚠️ No TV show found with the given ID.\n");
                    } else {
                        System.out.println("\n⚠️ Resource not found.\n");
                    }
                }
                default -> System.out.println("\n❌ Failed to update watch status. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while updating watch status: " + e.getMessage());
        }

        return false;
    }

    /**
     * Deletes a show from the user's watch history.
     *
     * @param showId   the ID of the show to delete
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public static void deleteFromWatchHistory(int showId, String username, String password) {
        String deleteUrl = WATCH_HISTORY_URL + "/" + showId;

        try {
            HttpResponse<String> response = HttpRequestUtil.sendDelete(deleteUrl, username, password);

            switch (response.statusCode()) {
                case 200 -> System.out.println("\n🗑️ Successfully removed show from your watch history.\n");
                case 404 -> {
                    String responseBody = response.body().toLowerCase();
                    // Distinguish between not in watch history, show not found, or generic 404
                    if (responseBody.contains("watch history")) {
                        System.out.println("\n⚠️ This show is not in your watch history.\n");
                    } else if (responseBody.contains("show")) {
                        System.out.println("\n⚠️ No TV show found with the given ID.\n");
                    } else {
                        System.out.println("\n⚠️ Resource not found.\n");
                    }
                }
                default -> System.out.println("\n❌ Failed to delete show. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while deleting from watch history: " + e.getMessage());
        }
    }
}
