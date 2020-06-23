package com.example.healthfull.DailyTarget;


/**
 * @author Chandan Aulakh
 * basic target Object to serve as input method into Firestore for {@link DailyTarget} class
 * has two private variables for water and food targets
 * This class is used as a template to store data and diplay retrived data
 */
public class TargetObject {

    private String waterTarget;
    private String foodTarget;

    //public constructor
    public TargetObject(){

    }

    public TargetObject(String waterTarget, String foodTarget){
        this.waterTarget= waterTarget;
        this.foodTarget = foodTarget;
    }

    //getters for the variables
    public String getWaterTarget() {
        return waterTarget;
    }

    public String getFoodTarget() {
        return foodTarget;
    }
}
