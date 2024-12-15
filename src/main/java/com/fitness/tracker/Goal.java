package com.fitness.tracker;

public class Goal {
    private String description;
    private double targetValue;
    private double progress;

    public Goal(String description, double targetValue){
        this.description=description;
        this.targetValue=targetValue;
        this.progress=0.0;
    }

    public void updateProgress(double progressDone){
        this.progress = progressDone;
        if(this.progress>targetValue) {
            this.progress = targetValue;
        }
    }

    public String getDescription(){
        return description;
    }
    public double getTargetValue(){
        return targetValue;
    }

    public boolean isGoalAchieved(){
        return this.progress>=targetValue;
    }

    public String toString(){
        return String.format("Goal[ Description: %s, Target: %.2f, Progress: %.2f ]",description, targetValue, progress);
    }
}
