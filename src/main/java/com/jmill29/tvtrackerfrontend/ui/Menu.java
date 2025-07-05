package com.jmill29.tvtrackerfrontend.ui;

import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.service.AuthService;
import com.jmill29.tvtrackerfrontend.service.ShowService;
import com.jmill29.tvtrackerfrontend.service.UserService;
import com.jmill29.tvtrackerfrontend.util.HttpRequestUtil;

import java.util.Scanner;

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
            System.out.println("==== TV Tracker CLI ====");
            int i = 1;
            boolean loginInfoNotNull = HttpRequestUtil.loginInfoNotNull(loggedInUsername, loggedInPassword);
            if (!loginInfoNotNull) {
                System.out.println(i++ + ". Register");
                System.out.println(i++ + ". Login");
            } else {
                System.out.println(i++ + ". View All TV Shows");
            }
            exitIndex = i;
            System.out.println(i++ + ". Exit");

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
                        continue;
                    } else {
                        handleLogin();
                    }
                    break;
            }
        } while (!choice.equals("" + exitIndex));
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
            System.out.println("\n✅ You are now logged in.\n");
        } else {
            System.out.println("\n❌ Login failed. Please check your credentials.\n");
        }
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getLoggedInPassword() {
        return loggedInPassword;
    }
}
