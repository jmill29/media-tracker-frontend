package com.jmill29.tvtrackerfrontend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jmill29.tvtrackerfrontend.dto.UserWatchHistoryRequest;
import com.jmill29.tvtrackerfrontend.dto.UserWatchHistoryResponse;
import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;

public class UserWatchHistoryService {

    private static final String WATCH_HISTORY_URL = "http://localhost:8080/api/watch-history";
    private static final Gson gson = new Gson();

    public static void fetchAndDisplayWatchHistory(String username, String password) {
        try {
            HttpResponse<String> response = HttpRequestUtil.sendGet(WATCH_HISTORY_URL, username, password);

            if (response.statusCode() == 200) {
                Type listType = new TypeToken<List<UserWatchHistoryResponse>>() {}.getType();
                List<UserWatchHistoryResponse> history = gson.fromJson(response.body(), listType);

                System.out.println("\n=== Your Watch History ===");
                for (UserWatchHistoryResponse record : history) {
                    System.out.println("\n" + record.getShowId() + ".");
                    System.out.printf("📺 %s (%s)\n", record.getShowName(), record.getStatus());
                    System.out.println("📄 " + record.getDescription());
                    System.out.println("🖼️  Image URL: " + record.getImageUrl());
                    System.out.println("📌 Status: " + record.getStatus());
                }
                System.out.println();
            } else if (response.statusCode() == 404) {
                System.out.println("\nℹ️ Your watch history is empty.\n");
            } else {
                System.out.println("\n❌ Failed to fetch watch history. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while fetching watch history: " + e.getMessage());
        }
    }

    public static boolean addToWatchHistory(int showId, String status, String username, String password) {
        UserWatchHistoryRequest request = new UserWatchHistoryRequest(showId, WatchStatus.fromDbValue(status));
        String jsonBody = new Gson().toJson(request);

        try {
            HttpResponse<String> response = HttpRequestUtil.sendPost(WATCH_HISTORY_URL, jsonBody, username, password);

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return true;
            } else if (response.statusCode() == 404) {
                System.out.println("\n⚠️ Show not found. Please check the Show ID and try again.\n");
            } else if (response.statusCode() == 409) {
                System.out.println("\n⚠️ This show is already in your watch history.\n");
            } else {
                System.out.println("\n❌ Failed to add show. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while adding to watch history: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateWatchStatus(int showId, String newStatus, String username, String password) {
        UserWatchHistoryRequest request = new UserWatchHistoryRequest(showId, WatchStatus.fromDbValue(newStatus));
        String jsonBody = new Gson().toJson(request);

        try {
            HttpResponse<String> response = HttpRequestUtil.sendPut(WATCH_HISTORY_URL, jsonBody, username, password);

            if (response.statusCode() == 200) {
                return true;
            } else if (response.statusCode() == 404) {
                String responseBody = response.body().toLowerCase();

                if (responseBody.contains("watch history")) {
                    System.out.println("\n⚠️ This show is not in your watch history.\n");
                } else if (responseBody.contains("show")) {
                    System.out.println("\n⚠️ No TV show found with the given ID.\n");
                } else {
                    System.out.println("\n⚠️ Resource not found.\n");
                }
            } else {
                System.out.println("\n❌ Failed to update watch status. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while updating watch status: " + e.getMessage());
        }

        return false;
    }

    public static void deleteFromWatchHistory(int showId, String username, String password) {
        String deleteUrl = WATCH_HISTORY_URL + "/" + showId;

        try {
            HttpResponse<String> response = HttpRequestUtil.sendDelete(deleteUrl, username, password);

            if (response.statusCode() == 200) {
                System.out.println("\n🗑️ Successfully removed show from your watch history.\n");
            } else if (response.statusCode() == 404) {
                String responseBody = response.body().toLowerCase();

                if (responseBody.contains("watch history")) {
                    System.out.println("\n⚠️ This show is not in your watch history.\n");
                } else if (responseBody.contains("show")) {
                    System.out.println("\n⚠️ No TV show found with the given ID.\n");
                } else {
                    System.out.println("\n⚠️ Resource not found.\n");
                }
            } else {
                System.out.println("\n❌ Failed to delete show. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("\n❌ An error occurred while deleting from watch history: " + e.getMessage());
        }
    }
}
