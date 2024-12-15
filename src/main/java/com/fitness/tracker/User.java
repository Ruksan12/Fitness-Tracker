package com.fitness.tracker;

import java.util.List;
import java.util.ArrayList;

public class User {

    private String name;
    private String username;
    private String password;
    private int age;
    private double weight;
    private double height;
    private List<Activity> activities;
    private Goal goal;


    public User(String name, String username, String password, int age , double weight, double height){
        this.name=name;
        this.age=age;
        this.weight=weight;
        this.height=height;
        this.username=username;
        this.password=password;
        this.activities=new ArrayList<>();
    }

    //setters and getters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }
    public double getWeight(){
        return weight;
    }
    public void setWeight(double weight){
        this.weight=weight;
    }
    public double getHeight(){
        return height;
    }
    public void setHeight(double height){
        this.height=height;
    }
    public void addActivity(Activity activity){activities.add(activity);}
    public List<Activity> getActivities(){return activities;}
    public void addGoal(Goal goal){this.goal=goal;}
    public Goal getGoal(){return this.goal;}

    public String toString(){
        return "User{ Name="+ name +
        ", Age=" + age +
        ", Weigth=" + weight +
        ", Height=" + height +
        " }";
    }

}
