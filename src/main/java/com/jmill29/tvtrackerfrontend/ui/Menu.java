package com.jmill29.tvtrackerfrontend.ui;

import java.util.Scanner;
import java.util.stream.Stream;

import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.enums.WatchStatus;
import com.jmill29.tvtrackerfrontend.service.AuthService;
import com.jmill29.tvtrackerfrontend.service.ShowService;
import com.jmill29.tvtrackerfrontend.service.UserService;
import com.jmill29.tvtrackerfrontend.service.UserWatchHistoryService;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;


/**
 * CLI menu for the TV Tracker application.
 * <p>
 * Handles user registration, login, and all watch history operations via a command-line interface.
 * Maintains login state and interacts with service classes for backend operations.
 * </p>
 */
public class Menu {

    private final Scanner scanner;
    String loggedInUsername;
    String loggedInPassword;

    /**
     * Constructs a Menu with the provided Scanner for user input.
     *
     * @param scanner the Scanner to use for reading user input
     */
    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the main menu and handles user interaction in a loop until exit is chosen.
     */
    public void displayMenu() {
        displayMenu(Integer.MAX_VALUE);
    }

    // Overload for testability: allows limiting the number of menu iterations
    /**
     * Displays the main menu and handles user interaction, limited to a maximum number of iterations (for testing).
     *
     * @param maxIterations the maximum number of menu loops to allow before exiting
     */
    public void displayMenu(int maxIterations) {
        int iterations = 0;
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

            int numChoice;
            try {
                numChoice = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input detected, please try again.\n");
                iterations++;
                continue;
            }
            if (numChoice < 1 || numChoice >= i) {
                System.out.println("\nInvalid input detected, please try again.\n");
                iterations++;
                continue;
            }
            // Use enhanced switch (rule switch) for clarity and modern style
            switch (choice) {
                case "1" -> {
                    if (loginInfoNotNull) {
                        ShowService.fetchAndDisplayShows(loggedInUsername, loggedInPassword);
                    } else {
                        handleRegistration();
                    }
                }
                case "2" -> {
                    if (loginInfoNotNull) {
                        UserWatchHistoryService.fetchAndDisplayWatchHistory(loggedInUsername, loggedInPassword);
                    } else {
                        handleLogin();
                    }
                }
                case "3" -> {
                    if (loginInfoNotNull) {
                        handleAddToWatchHistory();
                    }
                }
                case "4" -> handleUpdate();
                case "5" -> handleDeleteFromWatchHistory();
                case "6" -> handleLogout();
            }
            iterations++;
        } while (!choice.equals("" + exitIndex) && iterations < maxIterations);

        handleExit();
    }

    /**
     * Handles user registration by prompting for required fields and calling the UserService.
     */
    void handleRegistration() {
        System.out.println("\n=== Register New Account ===");

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (name == null || username == null || password == null || email == null ||
            name.isBlank() || username.isBlank() || password.isBlank() || email.isBlank()) {
            System.out.println("❌ All fields are required. Registration failed.");
            return;
        }

        UserRequest userRequest = new UserRequest(name, username, password, email);
        boolean success = UserService.registerUser(userRequest);

        if (success) {
            loggedInUsername = username;
            loggedInPassword = password;
            System.out.println("User successfully registered!");
        }
    }

    /**
     * Handles user login by prompting for credentials and calling the AuthService.
     */
    void handleLogin() {
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

    /**
     * Handles adding a show to the user's watch history by prompting for show ID and status.
     */
    void handleAddToWatchHistory() {
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

        // Use enhanced switch (rule switch)
        switch (choice) {
            case "A" -> status = watchStatusVals[0];
            case "B" -> status = watchStatusVals[1];
            case "C" -> status = watchStatusVals[2];
            case "D" -> status = watchStatusVals[3];
            default -> {
                System.out.println("Invalid input detected. Please try again.");
                return;
            }
        }

        try {
            boolean success = UserWatchHistoryService.addToWatchHistory(showId, status, loggedInUsername, loggedInPassword);
            if (success) {
                System.out.println("\n✅ Show successfully added to your watch history.\n");
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred while adding to watch history: " + e.getMessage());
        }
    }

    /**
     * Handles updating the watch status of a show in the user's watch history.
     */
    void handleUpdate() {
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

        // Use enhanced switch (rule switch)
        switch (choice) {
            case "A" -> status = watchStatusVals[0];
            case "B" -> status = watchStatusVals[1];
            case "C" -> status = watchStatusVals[2];
            case "D" -> status = watchStatusVals[3];
            default -> {
                System.out.println("Invalid input detected. Please try again.");
                return;
            }
        }

        try {
            boolean success = UserWatchHistoryService.updateWatchStatus(showId, status, loggedInUsername, loggedInPassword);
            if (success) {
                System.out.println("\n✅ Watch status updated successfully.\n");
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred while updating watch status: " + e.getMessage());
        }
    }

    /**
     * Handles deleting a show from the user's watch history by prompting for show ID.
     */
    void handleDeleteFromWatchHistory() {
        System.out.println("\n=== Delete a Show from Watch History ===");
        System.out.print("Enter the show ID to delete: ");
        int showId;
        try {
            showId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number. Please enter a valid Show ID.");
            return;
        }
        if (loggedInUsername == null || loggedInPassword == null) {
            System.out.println("❌ You must be logged in to delete from watch history.");
            return;
        }
        try {
            UserWatchHistoryService.deleteFromWatchHistory(showId, loggedInUsername, loggedInPassword);
        } catch (Exception e) {
            System.out.println("❌ An error occurred while deleting from watch history: " + e.getMessage());
        }
    }

    /**
     * Logs out the current user and clears login state.
     */
    void handleLogout() {
        loggedInUsername = null;
        loggedInPassword = null;
        System.out.println("\n👋 You have been logged out. Thanks for using TV Tracker!\n");
    }

    /**
     * Handles application exit and prints a farewell message.
     */
    void handleExit() {
        System.out.println("\n📺 Thanks for using TV Tracker. See you next time!\n");
    }

}
