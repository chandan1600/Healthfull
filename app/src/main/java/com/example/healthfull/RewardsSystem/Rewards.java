package com.example.healthfull.RewardsSystem;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Random;

/**
 * @author Chandan Aulakh
 * this class sets a users goal calories, lets the user redeem points and lets the user
 * redeem a recipe. It handles all requests to firebase
 */
public class Rewards extends AppCompatActivity{

    private static final String TAG = "Rewards";

    private static String random;
    private static double goalCalorie;
    private static double userCalorie = 2500;
    private TextView textViewRecipe;
    private EditText textWeight;
    private EditText textHeight;
    private EditText textAge;
    private Button buttonRedeem;

    //instance of firebase retrieved, reference to "recipes" collection and reference to "u1" document
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");
    private DocumentReference userPoint = db.collection("users").document("u1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        //id of xml components are stored in variable
        textViewRecipe = findViewById(R.id.view_recipe_data);
        textWeight = findViewById(R.id.edit_textWeight);
        textHeight = findViewById(R.id.edit_textHeight);
        textAge = findViewById(R.id.edit_textAge);
        buttonRedeem = findViewById(R.id.redeemButton);

        buttonRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointsDayTimer();
            }
        });
    }

    /**
     * sets goal calories for the user using input received from the edit text in xml.
     * uses Harris-Benedict formula to calculate calories
     * @param view
     * @return
     */
    public double setGoalCalories(View view){
        String inputWeight = textWeight.getText().toString();
        String inputHeight = textHeight.getText().toString();
        String inputAge = textAge.getText().toString();

        double weight  = Double.parseDouble(inputWeight);
        double height = Double.parseDouble(inputHeight);
        double age = Double.parseDouble(inputAge);

        goalCalorie= 1.2*(66.5 + (13.75*weight)+(5.003*height)-(6.775*age));
        Toast.makeText(this, "goal calories set", Toast.LENGTH_SHORT).show();
        return goalCalorie;
    }

    public boolean compareCalories(){
        if(userCalorie>goalCalorie){
            return true;
        }
        return false;
    }

    //subtractPoints reduces the user points by 50 after recipe redemption
    public void subtractPoints(){
        userPoint.update("points", FieldValue.increment(-50));
    }

    /**
     * redeem points is a method to increment users points by 10 if they have met goal calories
     * else they will receive a toast message that goal is not met
     */
    public void redeemPoints(){
        if(compareCalories()) {
            userPoint.update("points", FieldValue.increment(10))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Rewards.this, "you received 10 points", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "redeem points successful");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Rewards.this, "you did not meet your calorie goal", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"could not redeem points");
                }
            });
        }
    }

    /**
     * this is a timer for the redeem points button, the redeem options method will only run
     * once a day. After that the button can be clicked but will not run redeem points.
     */
    public void pointsDayTimer(){
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences settings = getSharedPreferences("PREFS",0);
        int lastDay = settings.getInt("day",0);

        if(lastDay!= currentDay){
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("day", currentDay);
            editor.commit();
            redeemPoints();
        }
        else{
            Toast.makeText(Rewards.this, "you have redeemed today's points!", Toast.LENGTH_SHORT).show();
        }
    }

    //random number generator
    public String ranInt(){
        Random ran = new Random();
        int r1 = ran.nextInt(4)+1;
        random = String.valueOf(r1);
        return random;
    }

    /**
     * get recipe retrieves recipes from the database and stores it into the recipe object to be
     * displayed in the text view. 50 points are then subtracted from the user if succesfully
     * performed.
     * @param view
     */
    public void getRecipe(View view){
        ranInt();
            recipesRef
                    .whereEqualTo("id",random)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String display="";

                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                                RecipeObject rec = documentSnapshots.toObject(RecipeObject.class);

                                String id = rec.getId();
                                String ingredients = rec.getIngredients();
                                String name = rec.getName();
                                String recipe = rec.getRecipe();

                                display += "id: "+id+"\n\ningredients: "+ingredients+"\n\nname: "+name
                                        +"\n\nrecipe: "+recipe+"\n\n\n\n";
                            }
                            textViewRecipe.setText(display);
                            Toast.makeText(Rewards.this, "Recipe Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });
            subtractPoints();
    }

}

