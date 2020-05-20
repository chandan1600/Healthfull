package com.example.healthfull.search;

public class FoodSearchResult {
    private String name, description;
    private float servingSize;
    /*
    include all other nutritional information
     */

    public FoodSearchResult() {
    }

    public FoodSearchResult(String name) {
        this.name = name;
        this.description = "";
        this.servingSize = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getServingSize() {
        return servingSize;
    }
}
