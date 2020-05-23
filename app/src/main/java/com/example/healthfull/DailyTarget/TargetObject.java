package com.example.healthfull.DailyTarget;

public class TargetObject {

    private String waterTarget;
    private String foodTarget;

    public TargetObject(){

    }

    public TargetObject(String waterTarget, String foodTarget){
        this.waterTarget= waterTarget;
        this.foodTarget = foodTarget;
    }

    public String getWaterTarget() {
        return waterTarget;
    }

    public String getFoodTarget() {
        return foodTarget;
    }
}
