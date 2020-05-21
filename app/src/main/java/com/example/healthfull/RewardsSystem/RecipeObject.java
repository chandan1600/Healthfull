package com.example.healthfull.RewardsSystem;

public class RecipeObject {
    private String id;
    private String ingredients;
    private String name;
    private String recipe;

    public RecipeObject(){
        //public constructor
    }

    public RecipeObject(String id, String ingredients, String name, String recipe){
        this.id = id;
        this.ingredients = ingredients;
        this.name = name;
        this.recipe=recipe;
    }

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
