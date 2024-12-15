package com.fitness.tracker;

import java.util.List;
import java.util.ArrayList;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user);
    }

    public User loginUser(String username, String password){
        for(User user : users){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                System.out.println("Login Successfull!");
                return user;
            }
        }
        System.out.println("Inavalid username or password.");
        return null;
    }

    public boolean isUsernameTaken(String username){
        for(User user : users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void addActivityToUser(String userName, Activity activity) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                user.addActivity(activity);
                System.out.println("Activity added to " + userName + ":" + activity);
                return;
            }
        }
        System.out.println("User not found");
    }

    public void setGoalForUser(String userName, Goal goal) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                System.out.println("Goal set for " + userName + ": " + goal);
                return;
            }
        }
        System.out.println("User not found");
    }

    public void updateGoalProgress(String userName, double progressDone) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                Goal goal = user.getGoal();
                if (goal != null) {
                    goal.updateProgress(progressDone);
                    System.out.println("Updated progress for " + userName + ": " + goal);
                } else {
                    System.out.println("No goal set for " + userName);
                }
                return;
            }
        }
        System.out.println("User not found");
    }

    public List<User> getUsers() {
        return users;
    }

    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No Users Found");
        } else {
            for (User user : users) {
                System.out.println(user);
                if (user.getGoal() != null) {
                    System.out.println(user.getGoal());
                } else {
                    System.out.println("No goal set");
                }
                if (!user.getActivities().isEmpty()) {
                    System.out.println("Activities:");
                    for (Activity activity : user.getActivities()) {
                        System.out.println(activity);
                    }
                } else {
                    System.out.println("No Activities recorded");
                }
            }
        }
    }
}


