package com.example.healthfull.RewardsSystem;


/*
retrieve total calories for day and goal calories
compare total and goal calories, if total is => then user point count is +10
for reward, user point must be => 50
if points are => 50, a reward is selected by random from database
once redeemed, 50 points are subtracted from user point count
 */


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class Rewards extends AppCompatActivity{

    private static final String TAG = "Rewards";
    private static final String keyID = "id";

    private TextView textViewRecipe;

    private static Random randNum = new Random();
    private static int ran = randNum.nextInt(3) + 1;
    private static String random = Integer.toString(ran);

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");
    private DocumentReference userRef = db.collection("user").document("u1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        textViewRecipe = findViewById(R.id.view_recipe_data);
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

    public int getUserPoint(){
        final String[] userPointCount = new String[1];
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            userPointCount[0] = documentSnapshot.getString("points");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rewards.this, "could not retrieve points", Toast.LENGTH_SHORT).show();
                    }
                });
        return Integer.parseInt(userPointCount[0]);
    }

    //compares userCalories and GoalCalories to see if user achieved goal, returns true if successful
    public boolean compareCalories(){
        return getUserGoalCalories() < getUserCalories();
    }

    //subtractPoints reduces the user points by 50 after recipe redemption
    public void subtractPoints(){
        DocumentReference userPoint = db.collection("users").document("u1");
        userPoint.update("points", FieldValue.increment(-50));
    }

    //method to return true if user has more than 50 points
    public boolean checkUserPoints(){
        if(getUserPoint()<50){
            return false;
        }
        return true;
    }

    //user clicks to redeem points for the day, if calories are higher than goal calories
    public void redeemPoints(View view){
        if(compareCalories()) {
            DocumentReference userPoint = db.collection("users").document("u1");
            userPoint.update("points", FieldValue.increment(10));
            Toast.makeText(this, "you received 10 points", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "you did not meet your calorie goal", Toast.LENGTH_SHORT).show();
        }
    }

    //getRecipe retrieves a recipe if user has points, recipe is randomised using randomInt
    public void getRecipe(View view){
        if(checkUserPoints()){
            recipesRef
                    .whereEqualTo(keyID, random)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String info = "";
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                RecipeObject recipes = documentSnapshot.toObject(RecipeObject.class);

                                String id = recipes.getId();
                                String ingredients = recipes.getIngredients();
                                String name = recipes.getName();
                                String recipe = recipes.getRecipe();

                                info += "id: "+id+"\nname: "+name+"\ningredients: "+ingredients+"\nrecipe: "+recipe;
                            }
                            textViewRecipe.setText(info);
                        }
                    });
            subtractPoints();
        }
        else{
            Toast.makeText(Rewards.this, "insufficient points", Toast.LENGTH_SHORT).show();
        }
    }

/*
figure out how to have a 24 hour gap in between redeem points
add a point count somewhere that updates
 */

}

