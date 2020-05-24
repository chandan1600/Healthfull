package com.example.healthfull.search;

/**
 * INCOMPLETE
 * FoodSearch is responsible for searching the Firebase food collection asynchronously for food tags
 * matching the query. It includes callbacks for completion with a FoodSearchResults object
 * containing all the returned results
 */
public class FoodSearch {

    /**
     *
     * @param query
     * @return
     */
    public static FoodSearchResults Search(String query) {
        FoodSearchResults results = new FoodSearchResults();

        results.add(new FoodSearchResult("1", "Food 1"));

        return results;
    }
}
