package com.example.healthfull.search_nutri;

public class NutrientObject
{

    String foodname;
    String carbohydrate;
    String fat;
    String protein;


    /**
     * default constructor
     */
    public NutrientObject(){
        //public constructor
    }

    public NutrientObject(String foodname, String carbohydrate, String fat, String protein){
        this.foodname = foodname;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.protein = protein;

    }


    public String getFoodName() {
        return foodname;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

}
