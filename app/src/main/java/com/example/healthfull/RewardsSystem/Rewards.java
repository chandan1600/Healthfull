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

import java.util.Random;

public class Rewards extends AppCompatActivity{

    private static final String TAG = "Rewards";
    private static final String keyID = "id";

    private static double goalCalorie;
    private static double userCalorie = 2500;
    private TextView textViewRecipe;
    private EditText textWeight;
    private EditText textHeight;
    private EditText textAge;

    private static Random randNum = new Random();
    private static int ran = randNum.nextInt(4);
    private static String random = Integer.toString(ran);

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");
    private DocumentReference userPoint = db.collection("users").document("u1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        textViewRecipe = findViewById(R.id.view_recipe_data);
        textWeight = findViewById(R.id.edit_textWeight);
        textHeight = findViewById(R.id.edit_textHeight);
        textAge = findViewById(R.id.edit_textAge);
    }

    public double setGoalCalories(View view){
        String inputWeight = textWeight.getText().toString();
        String inputHeight = textHeight.getText().toString();
        String inputAge = textAge.getText().toString();

        double weight  = Double.parseDouble(inputWeight);
        double height = Double.parseDouble(inputHeight);
        double age = Double.parseDouble(inputAge);

        goalCalorie= 1.2*(66.5 + (13.75*weight)+(5.003*height)-(6.775*age));
        Toast.makeText(this, (int) goalCalorie, Toast.LENGTH_SHORT).show();
        return goalCalorie;
    }

    //here i have tried to parse the String array returned by the two methods
    //however this experiences the same null problem
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


    //user clicks to redeem points for the day, if calories are higher than goal calories
    public void redeemPoints(View view){
        if(compareCalories()) {
            userPoint.update("points", FieldValue.increment(10))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Rewards.this, "you received 10 points", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Rewards.this, "you did not meet your calorie goal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //getRecipe retrieves a recipe if user has points, recipe is randomised using randomInt
    public void getRecipe(View view){
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

