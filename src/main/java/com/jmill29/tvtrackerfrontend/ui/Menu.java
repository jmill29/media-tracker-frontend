package com.jmill29.tvtrackerfrontend.ui;

import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import com.jmill29.tvtrackerfrontend.service.AuthService;
import com.jmill29.tvtrackerfrontend.service.ShowService;
import com.jmill29.tvtrackerfrontend.service.UserService;
import com.jmill29.tvtrackerfrontend.service.UserWatchHistoryService;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {

    private final Scanner scanner;
    private String loggedInUsername;
    private String loggedInPassword;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMenu() {
        String choice;
        int exitIndex;

        do {
            System.out.println("\n==== TV Tracker CLI ====");
            int i = 1;
            boolean loginInfoNotNull = HttpRequestUtil.loginInfoNotNull(loggedInUsername, loggedInPassword);
            if (!loginInfoNotNull) {
                System.out.println(i++ + ". Register");
                System.out.println(i++ + ". Login");
            } else {
                System.out.println(i++ + ". View All TV Shows");
                System.out.println(i++ + ". View Watch History");
                System.out.println(i++ + ". Add Show to Watch History");
                System.out.println(i++ + ". Update Watch Status");
                System.out.println(i++ + ". Delete a show from Watch History");
                System.out.println(i++ + ". Logout");
            }
            exitIndex = i;
            System.out.println(i++ + ". Exit");

            System.out.print("\n👉 Enter your choice here: ");
            choice = scanner.nextLine().trim();

            int numChoice = Integer.parseInt(choice);
            if (numChoice < 1 || numChoice >= i) {
                System.out.println("\nInvalid input detected, please try again.\n");
                continue;
            }
            switch (choice) {
                case "1":
                    if (loginInfoNotNull) {
                        ShowService.fetchAndDisplayShows(loggedInUsername, loggedInPassword);
                    } else {
                        handleRegistration();
                    }
                    break;
                case "2":
                    if (loginInfoNotNull) {
                        UserWatchHistoryService.fetchAndDisplayWatchHistory(loggedInUsername, loggedInPassword);
                    } else {
                        handleLogin();
                    }
                    break;
                case "3":
                    if (loginInfoNotNull) {
                        handleAddToWatchHistory();
                    } else {
                        continue;
                    }
                    break;
                case "4":
                    handleUpdate();
                    break;
                case "5":
                    handleDeleteFromWatchHistory();
                case "6":
                    handleLogout();
            }
        } while (!choice.equals("" + exitIndex));

        handleExit();
    }

    private void handleRegistration() {
        System.out.println("\n=== Register New Account ===");

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        UserRequest userRequest = new UserRequest(name, username, password, email);
        boolean success = UserService.registerUser(userRequest);

        if (success) {
            loggedInUsername = username;
            loggedInPassword = password;
            System.out.println("User successfully registered!");
        }
    }

    private void handleLogin() {
        System.out.println("\n=== Login ===");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = AuthService.login(username, password);

        if (success) {
            loggedInUsername = username;
            loggedInPassword = password;
            System.out.println("\n✅ Hi, " + username + "! you are now logged in.\n");
        } else {
            System.out.println("\n❌ Login failed. Please check your credentials.\n");
        }
    }

    private void handleAddToWatchHistory() {
        System.out.println("\n=== Add Show to Watch History ===");

        System.out.print("Enter Show ID: ");
        int showId;

        try {
            showId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number. Please enter a valid Show ID.");
            return;
        }

        String[] watchStatusVals = Stream.of(WatchStatus.values())
                                .map(WatchStatus::getDbValue)
                                .toArray(String[]::new);

        System.out.println("Enter Watch Status:");
        System.out.println("\tA. " + watchStatusVals[0]);
        System.out.println("\tB. " + watchStatusVals[1]);
        System.out.println("\tC. " + watchStatusVals[2]);
        System.out.println("\tD. " + watchStatusVals[3]);

        System.out.print("\n👉 Enter your choice here: ");
        String choice = scanner.nextLine();
        String status;

        switch (choice) {
            case "A":
                status = watchStatusVals[0];
                break;
            case "B":
                status = watchStatusVals[1];
                break;
            case "C":
                status = watchStatusVals[2];
                break;
            case "D":
                status = watchStatusVals[3];
                break;
            default:
                System.out.println("Invalid input detected. Please try again.");
                return;
        }

        boolean success = UserWatchHistoryService.addToWatchHistory(showId, status, loggedInUsername, loggedInPassword);

        if (success) {
            System.out.println("\n✅ Show successfully added to your watch history.\n");
        }
    }

    private void handleUpdate() {
        System.out.println("\n=== Update Watch Status ===");

        System.out.print("Enter the ID of the show you want to update: ");
        String showIdInput = scanner.nextLine().trim();

        int showId;
        try {
            showId = Integer.parseInt(showIdInput);
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid show ID. Please enter a numeric value.\n");
            return;
        }

        String[] watchStatusVals = Stream.of(WatchStatus.values())
                .map(WatchStatus::getDbValue)
                .toArray(String[]::new);

        System.out.println("Enter Watch Status:");
        System.out.println("\tA. " + watchStatusVals[0]);
        System.out.println("\tB. " + watchStatusVals[1]);
        System.out.println("\tC. " + watchStatusVals[2]);
        System.out.println("\tD. " + watchStatusVals[3]);

        System.out.print("\n👉 Enter your choice here: ");
        String choice = scanner.nextLine();
        String status;

        switch (choice) {
            case "A":
                status = watchStatusVals[0];
                break;
            case "B":
                status = watchStatusVals[1];
                break;
            case "C":
                status = watchStatusVals[2];
                break;
            case "D":
                status = watchStatusVals[3];
                break;
            default:
                System.out.println("Invalid input detected. Please try again.");
                return;
        }

        boolean success = UserWatchHistoryService.updateWatchStatus(showId, status, loggedInUsername, loggedInPassword);

        if (success) {
            System.out.println("\n✅ Watch status updated successfully.\n");
        }
    }

    private void handleDeleteFromWatchHistory() {
        System.out.println("\n=== Delete a Show from Watch History ===");
        System.out.print("Enter the show ID to delete: ");
        int showId = Integer.parseInt(scanner.nextLine());

        UserWatchHistoryService.deleteFromWatchHistory(showId, loggedInUsername, loggedInPassword);
    }

    private void handleLogout() {
        loggedInUsername = null;
        loggedInPassword = null;
        System.out.println("\n👋 You have been logged out. Thanks for using TV Tracker!\n");
    }

    private void handleExit() {
        System.out.println("\n📺 Thanks for using TV Tracker. See you next time!\n");
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getLoggedInPassword() {
        return loggedInPassword;
    }
}
