package com.fitness.tracker;

public class Activity {
    private String type;
    private int duration ;
    private double caloriesBurned;

    public Activity(String type, int duration, double caloriesBurned){
        this.type=type;
        this.duration=duration;
        this.caloriesBurned=caloriesBurned;
    }

    public String getActivityType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public int getDuration(){
        return duration;
    }
    public void setDuration(){
        this.duration = duration;
    }
    public double getCaloriesBurned(){
        return caloriesBurned;
    }
    public void setCaloriesBurned(){
        this.caloriesBurned=caloriesBurned;
    }

    public String toString(){
        return String.format("Activity[ type:%s, duration:%d minutes , calories Burned:%.2f ",
                type, duration, caloriesBurned);
    }
}
