package com.example.healthfull.RewardsSystem;


/*
retrieve total calories for day and goal calories
compare total and goal calories, if total is => then user point count is +10
for reward, user point must be => 50
if points are => 50, a reward is selected by random from database
once redeemed, 50 points are subtracted from user point count
 */


import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class Rewards {
    public static int total_cal =2500; //consumed calories - needs to be replaced by real daily calories
    public static int goal = 2000;//goal calories - needs to be replaced by target calories
    public static user dummyUser = new user(); // need to retrieve users count
    public static int currentPoint = dummyUser.getCount();
    private static final String TAG = "DocSnippets";

    public static boolean compareCalories(){
        if(total_cal >= goal){
            System.out.println("goal was met");
            return true;
        }
        else{
            System.out.println("goal was not met");
            return false;
        }
    }

    public static void assignPoints() {
        if (compareCalories()) {
            dummyUser.setCount(currentPoint+10);
            System.out.println(dummyUser.getCount());
        }
    }

    public static int redeemRecipe(){
        if(dummyUser.getCount()>=50){
            System.out.println("you have sufficient points");
            //get recipe method here
            dummyUser.setCount(dummyUser.getCount()-50);
        }
        else{
            System.out.println("you have " +dummyUser.getCount() +", this is not sufficient");
        }
        return dummyUser.getCount();
    }


    public static void getRecipe(){
        Random randNum = new Random();
        int ran = randNum.nextInt(3)+1;
        String random = String.valueOf(ran);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipeRef = db.collection("recipes");
        final Query query = recipeRef.whereEqualTo("id", random);

        db.collection("recipes")
                .whereEqualTo("id",random)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                            }
                        }
                            else {
                                Log.d(TAG,"error getting document", task.getException());
                            }
                    }
                });
    }

    public static void main(String[] args) {
        compareCalories();
        assignPoints();
    }
}
