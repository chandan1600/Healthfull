package com.example.healthfull.search;

public class FoodSearch {

    public static FoodSearchResults Search(String query) {
        FoodSearchResults results = new FoodSearchResults();

        results.add(new FoodSearchResult("Food 1"));

        return results;
    }
}