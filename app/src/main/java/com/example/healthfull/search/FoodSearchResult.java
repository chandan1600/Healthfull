package com.example.healthfull.search;

/**
 * FoodSearchResult contains data retrieved from Firebase for a specific food
 */
public class FoodSearchResult {
    private String id, name, description;
    private float servingSize;
    /*
    include all other nutritional information
     */

    /**
     * Default constructor
     */
    public FoodSearchResult() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.servingSize = 0;
    }

    /**
     * Constructor sets food id and name
     * @param id Firebase ID for the food
     * @param name name of the food
     */
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
