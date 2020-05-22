package com.example.healthfull.search;

public class FoodSearchResult {
    private String id, name, description;
    private float servingSize;
    /*
    include all other nutritional information
     */

    public FoodSearchResult() {
    }

    public FoodSearchResult(String id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.servingSize = 0;
    }

    public String getId() {
        return id;
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
