package com.example.healthfull.RewardsSystem;

public class User {

    private String goalCalories;
    private String userCalories;

    public User(){
    }

    public User(String goalCalories, String userCalories){
        this.goalCalories = goalCalories;
        this.userCalories = userCalories;
    }

    public String getGoalCalories() {
        return goalCalories;
    }

    public String getUserCalories() {
        return userCalories;
    }
}
