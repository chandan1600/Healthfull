package com.example.healthfull.entries;

/**
 * NewWaterEntryAdder is an asynchronous class that has a short lifecycle to add a log to Firebase
 * for the currently logged in user
 */
public class NewWaterEntryAdder extends NewFoodEntryAdder {

    /**
     * Constructor sets the id of Water (as per Firebase ID) for the entry adder
     */
    public NewWaterEntryAdder() {
        super("uXRBMlRtnW9XMrZb0qUs");
    }
}
