package com.jmill29.tvtrackerfrontend.ui;

import com.jmill29.tvtrackerfrontend.dto.UserRequest;
import com.jmill29.tvtrackerfrontend.service.UserService;

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
        boolean loginInfoNull = false;

        do {
            System.out.println("==== TV Tracker CLI ====");
            int i = 1;
            loginInfoNull = (loggedInPassword == null || loggedInPassword.isBlank()) || (loggedInPassword == null || loggedInPassword.isBlank());
            if (loginInfoNull) {
                System.out.println(i++ + ". Register");
                System.out.println(i++ + ". Login");
            }
            exitIndex = i;
            System.out.println(i++ + ". Exit");

            choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    if (exitIndex == 1) {
                        continue;
                    } else {
                        handleRegistration();
                    }
                    break;
                case "2":
                    if (loginInfoNull) {
                        handleLogin();
                    } else {
                        continue;
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
        loggedInUsername = scanner.nextLine();

        System.out.println("Password: ");
        loggedInPassword = scanner.nextLine();

        System.out.println("\n✅ Login info stored locally. You'll use these credentials for API requests.\n");
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getLoggedInPassword() {
        return loggedInPassword;
    }
}
