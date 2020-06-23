package com.example.healthfull.RewardsSystem;

/**
 * @author Chandan Aulakh
 * RecipeObject serves as a class for storing the data gained from a document snapshot
 * It is then used to display the data throught text view
 * used by {@link Rewards} class.
 */
public class RecipeObject {
    private String id;
    private String ingredients;
    private String name;
    private String recipe;

    //public constructor
    public RecipeObject(){
    }

    public RecipeObject(String id, String ingredients, String name, String recipe){
        this.id = id;
        this.ingredients = ingredients;
        this.name = name;
        this.recipe=recipe;
    }

    //getters for variables
    public String getId() {
        return id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }
}
