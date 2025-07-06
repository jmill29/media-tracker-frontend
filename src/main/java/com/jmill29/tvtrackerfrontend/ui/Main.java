package com.jmill29.tvtrackerfrontend.ui;

import java.util.Scanner;

/**
 * Entry point for the TV Tracker CLI application.
 * Initializes the menu system and begins user interaction.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize scanner for user input and ensure it is closed automatically
        try (Scanner scanner = new Scanner(System.in)) {
            // Instantiate and display the main menu
            Menu menu = new Menu(scanner);
            menu.displayMenu();
        }
    }
}
