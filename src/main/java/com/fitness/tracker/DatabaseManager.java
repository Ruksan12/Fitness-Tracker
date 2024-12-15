package com.fitness.tracker;

import java.sql.*;

public class DatabaseManager {

    // Method to create the SQLite database if it doesn't exist
    public static void createDatabase() {
        String url = "jdbc:sqlite:fitness_tracker.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Database connection established.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table dropped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method to create the users table
    public static void createUsersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                age INTEGER,
                weight REAL,
                height REAL
            );
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to create goals table
    public static void createGoalsTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS goals (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                description TEXT,
                target_value REAL,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Goals table created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to create activities table
    public static void createActivitiesTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS activities (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                activity_type TEXT,
                duration INTEGER,
                calories_burned REAL,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Activities table created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add a new user to the database
    public static void addUser(String name, String username, String password, int age, double weight, double height) {
        String sql = "INSERT INTO users(name, username, password, age, weight, height) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setInt(4, age);
            pstmt.setDouble(5, weight);
            pstmt.setDouble(6, height);
            pstmt.executeUpdate();
            System.out.println("User added.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a user is found
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Return false if no user is found
    }
    // Method to get user from the database for login
    public static User getUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("age"),
                        rs.getDouble("weight"),
                        rs.getDouble("height")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to add a new goal for a user
    public static void addGoal(String username, Goal goal) {
        String sql = """
            INSERT INTO goals(user_id, description, target_value)
            VALUES ((SELECT id FROM users WHERE username = ?), ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, goal.getDescription());
            pstmt.setDouble(3, goal.getTargetValue());
            pstmt.executeUpdate();
            System.out.println("Goal added for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add a new activity for a user
    public static void addActivity(String username, Activity activity) {
        String sql = """
            INSERT INTO activities(user_id, activity_type, duration, calories_burned)
            VALUES ((SELECT id FROM users WHERE username = ?), ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, activity.getActivityType());
            pstmt.setInt(3, activity.getDuration());
            pstmt.setDouble(4, activity.getCaloriesBurned());
            pstmt.executeUpdate();
            System.out.println("Activity added for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to list all users in the database
    public static void listUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fitness_tracker.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("User: " + rs.getString("name") + ", Age: " + rs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
