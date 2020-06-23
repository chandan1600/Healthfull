package com.example.healthfull.recommendation;

public class RecObject {

    private String name;
    private String protein;
    private String carbohydrate;
    private String fat;
    private String serving_size;

    public RecObject(){

    }

    public RecObject(String name, String protein, String carbohydrate, String fat, String serving_size){
        this.name = name;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.serving_size =serving_size;
    }

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

