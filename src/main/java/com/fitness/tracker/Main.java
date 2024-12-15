package com.fitness.tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize database and create necessary tables
        DatabaseManager dbManager = new DatabaseManager();
        DatabaseManager.createDatabase();
        DatabaseManager.createUsersTable();
        DatabaseManager.createGoalsTable();
        DatabaseManager.createActivitiesTable();

        System.out.println("Welcome to the Fitness Tracker Application!");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option:");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser(dbManager, sc);
                    break;
                case 2:
                    User loggedInUser = loginUser(dbManager, sc);
                    if (loggedInUser != null) {
                        userMenu(loggedInUser, dbManager, sc);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the application...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Method to register a new user
    private static void registerUser(DatabaseManager dbManager, Scanner sc) {
        System.out.println("\n--- Register ---");
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        // Check if the username is already taken
        if (dbManager.isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please choose a different one.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();

        System.out.print("Enter weight (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Enter height (cm): ");
        double height = sc.nextDouble();
        sc.nextLine(); // consume newline

        // Register the user in the database
        dbManager.addUser(name, username, password, age, weight, height);
        System.out.println("Registration successful!");
    }

    // Method for user login
    private static User loginUser(DatabaseManager dbManager, Scanner sc) {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        // Fetch the user from the database
        User user = dbManager.getUser(username, password);
        if (user != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please try again.");
        }
        return user;
    }

    // Menu for logged-in users
    private static void userMenu(User loggedInUser, DatabaseManager dbManager, Scanner sc) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Add Goal");
            System.out.println("2. Add Activity");
            System.out.println("3. View Progress");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addGoal(loggedInUser, dbManager, sc);
                    break;
                case 2:
                    addActivity(loggedInUser, dbManager, sc);
                    break;
                case 3:
                    viewProgress(loggedInUser, dbManager);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Method to add a goal for the user
    private static void addGoal(User user, DatabaseManager dbManager, Scanner sc) {
        System.out.println("\n--- Set a Goal ---");
        System.out.print("Enter goal description (e.g., Lose 5kg): ");
        String goalDescription = sc.nextLine();

        System.out.print("Enter the target value for the goal: ");
        double targetValue = sc.nextDouble();
        sc.nextLine(); // consume newline

        Goal goal = new Goal(goalDescription, targetValue);
        user.addGoal(goal);
        dbManager.addGoal(user.getName(), goal); // Store goal in database
        System.out.println("Goal added: " + goal);
    }

    // Method to add an activity for the user
    private static void addActivity(User user, DatabaseManager dbManager, Scanner sc) {
        System.out.println("\n--- Add an Activity ---");
        System.out.print("Enter activity type (e.g., Running, Cycling): ");
        String activityType = sc.nextLine();

        System.out.print("Enter duration in minutes: ");
        int duration = sc.nextInt();

        System.out.print("Enter calories burned: ");
        double caloriesBurned = sc.nextDouble();
        sc.nextLine(); // consume newline

        Activity activity = new Activity(activityType, duration, caloriesBurned);
        user.addActivity(activity);
        dbManager.addActivity(user.getName(), activity); // Store activity in database
        System.out.println("Activity added: " + activity);
    }

    // Method to view the user's progress
    private static void viewProgress(User user, DatabaseManager dbManager) {
        System.out.println("\n--- User Information ---");
        System.out.println(user);


        if (user.getGoal() != null) {
            System.out.println("Current Goal: " + user.getGoal());
        } else {
            System.out.println("No goal set.");
        }

        System.out.println("\nActivities:");
        if (user.getActivities().isEmpty()) {
            System.out.println("No activities recorded.");
        } else {
            for (Activity activity : user.getActivities()) {
                System.out.println(activity);
            }
        }
    }
}
