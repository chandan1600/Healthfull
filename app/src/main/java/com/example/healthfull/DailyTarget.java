package com.example.healthfull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DailyTarget extends AppCompatActivity {

    private static final String TAG = "DailyTarget";//to log errors
    public static final String keyWaterTarget = "waterTarget";
    public static final String keyFoodTarget = "FoodTarget";//your target "key"

    public EditText editTextFoodTarget;//this is for the xml typing box
    public EditText editTextWaterTarget;

    //connection to fire base and calling the users collection through document reference
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final DocumentReference targetRef = db.collection("users").document("u1");
    public static Map<String, Object> targets = new HashMap<>();//creates map to merge data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_target);

        editTextFoodTarget.equals(R.id.edit_textFoodTarget);//this creates a link to the editing box on the xml
        editTextWaterTarget.equals(R.id.edit_textWaterTarget);
    }

    public void setFoodTarget(View view){
        String FoodTarget = editTextFoodTarget.getText().toString();

        targets.put(keyFoodTarget, FoodTarget);//target is formatted into map targets

        //target is merged into the database, it is merged so it does not overwrite
        //success and failure listeners are added to check if it worked
        targetRef.set(targets, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DailyTarget.this, "Food Target was set!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DailyTarget.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void setWaterTarget(View view){
        String WaterTarget = editTextWaterTarget.getText().toString();

        targets.put(keyWaterTarget, WaterTarget);//target is formatted into map targets

        //target is merged into the database, it is merged so it does not overwrite
        //success and failure listeners are added to check if it worked
        targetRef.set(targets, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DailyTarget.this, "Water Target was set!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DailyTarget.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}



