package com.example.healthfull.recommendation;

import com.example.healthfull.DailyTarget.DailyTarget;

/**
 * @author Chandan Aulakh
 * basic recommendation object class that is used by {@link Recommendation}
 * Used to store database information for presentation in dispplay field
 */
public class RecObject {

    private String name;
    private String protein;
    private String carbohydrate;
    private String fat;
    private String serving_size;

    //public constructor
    public RecObject(){

    }

    public RecObject(String name, String protein, String carbohydrate, String fat, String serving_size){
        this.name = name;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.serving_size =serving_size;
    }

    //getters for the variables
    public String getName() {
        return name;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public String getFat() {
        return fat;
    }

    public String getServing_size() {
        return serving_size;
    }
}

