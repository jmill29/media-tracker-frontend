package com.jmill29.tvtrackerfrontend.ui;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        menu.displayMenu();
        scanner.close();
    }
}
