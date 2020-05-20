package com.example.healthfull.RewardsSystem;


/*
retrieve total calories for day and goal calories
compare total and goal calories, if total is => then user point count is +10
for reward, user point must be => 50
if points are => 50, a reward is selected by random from database
once redeemed, 50 points are subtracted from user point count
 */


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Rewards extends AppCompatActivity{

    private static final String TAG = "Rewards";
    private static final String keyName = "name";
    private static final String keyID = "id";
    private static final String keyIngredient = "ingredients";
    private static final String keyRecipe = "recipe";

    private EditText editTextName;
    private EditText editTextID;
    private EditText editTextIngredient;
    private EditText editTextRecipe;
    private TextView textViewRecipe;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference recipeRef = db.collection("recipes").document("testRecipe");
    private DocumentReference userRef = db.collection("user").document("u1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        editTextName = findViewById(R.id.edit_textName);
        editTextID = findViewById(R.id.edit_text_id);
        editTextIngredient = findViewById(R.id.edit_text_ingredient);
        editTextRecipe = findViewById(R.id.edit_text_recipe);
        textViewRecipe = findViewById(R.id.view_recipe_data);
    }

    //ignore saveRecipe method, i was only testing things out, this is not part of the userStory requirement
    public void saveRecipe(View V){
        String name = editTextName.getText().toString();
        String id = editTextID.getText().toString();
        String ingredients = editTextID.getText().toString();
        String recipe = editTextRecipe.getText().toString();

        Map<String, Object> RecipeCollection = new HashMap<>();
        RecipeCollection.put(keyName, name);
        RecipeCollection.put(keyID, id);
        RecipeCollection.put(keyIngredient, ingredients);
        RecipeCollection.put(keyRecipe, recipe);

        recipeRef.set(RecipeCollection)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Rewards.this, "recipe saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rewards.this,"Error Occurred", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    //getUserCalories retrieves the user's calories consumed in the day, currently using dummy account
    public int getUserCalories(){
        final String[] userCaloriesString = new String[1];
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            userCaloriesString[0] = documentSnapshot.getString("calories");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rewards.this, "user not found", Toast.LENGTH_SHORT).show();
                    }
                });
        return Integer.parseInt(userCaloriesString[0]);
    }

    //getUserGoalCalories retrieves the user's goal calories, currently using dummy account
    public int getUserGoalCalories(){
        final String[] userGoalCaloriesString = new String[1];
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            userGoalCaloriesString[0] = documentSnapshot.getString("goalCalories");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rewards.this, "user not found", Toast.LENGTH_SHORT).show();
                    }
                });
        return Integer.parseInt(userGoalCaloriesString[0]);
    }

    //compares userCalories and GoalCalories to see if user achieved goal, returns true if successful
    public boolean compareCalories(){
        return getUserGoalCalories() < getUserCalories();
    }

    //updatePoints updates the user's point count if compare calories is true
    // currently using dummy account. Not sure if logic is correct
    public void updatePoints(){
        if(compareCalories()) {
            DocumentReference userPoint = db.collection("users").document("u1");
            userPoint.update("points", FieldValue.increment(10));
        }
        else{
            Toast.makeText(this, "you did not meet your calorie goal", Toast.LENGTH_SHORT).show();
        }
    }

    //getRecipe retrieves a recipe, still need to randomise the recipe retrieval
    public void getRecipe(View V){
        recipeRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String name = documentSnapshot.getString(keyName);
                            String id = documentSnapshot.getString(keyID);
                            String ingredients = documentSnapshot.getString(keyIngredient);
                            String recipe = documentSnapshot.getString(keyRecipe);

                            textViewRecipe.setText("Name: "+name+"/n"+
                                                    "ID: "+id+"/n"+
                                                    "Ingredients: "+ingredients+"/n"+
                                                    "Recipe: "+recipe);                  
                        }else{
                            Toast.makeText(Rewards.this, "recipe does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rewards.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
    }


}

