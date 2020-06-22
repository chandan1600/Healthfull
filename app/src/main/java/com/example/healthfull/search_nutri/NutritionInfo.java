package com.example.healthfull.search_nutri;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.DailyTarget.DailyTarget;
import com.example.healthfull.DailyTarget.TargetObject;
import com.example.healthfull.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class NutritionInfo extends AppCompatActivity {

    EditText nutritionEditText;
    TextView nutritextView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_nutri);

        nutritionEditText = (EditText) findViewById(R.id.nutritionEditText);
        nutritextView = (TextView) findViewById(R.id.nutriTextView);
    }

    public String setSearch(View view){
        String searchInput = nutritionEditText.getText().toString();

        return searchInput;
    }

    public void getNutritionRecord(View view){
        String name;
        String searchInput = nutritionEditText.getText().toString();
        name = searchInput.substring(0, 1).toUpperCase() + searchInput.substring(1);

        foodRef
                .whereEqualTo("name",searchInput)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String display = "";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            NutrientObject nutrientObject = documentSnapshot.toObject(NutrientObject.class);

                            //String name = nutrientObject.getFoodName();
                            String carbohydrates = nutrientObject.getCarbohydrate();
                            String fat = nutrientObject.getFat();
                            String protein = nutrientObject.getProtein();

                            display += "Name: "+ name +
                                    "\n" + "Carbohydrates: " + carbohydrates +
                                    "\n" + "Fat: " + fat +
                                    "\n"+ "Proteins: " + protein;
                        }

                        nutritextView.setText(display);
                    }
                });
    }


//    public void loadNutrition(View view){
//        foodRef.document("food")
//                .collection("")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String info = "";
//
//                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
//                            TargetObject target = documentSnapshots.toObject(TargetObject.class);
//
//                            String water = target.getWaterTarget();
//                            String food = target.getFoodTarget();
//
//                            info += "water target: "+water+"\nfood target: "+food+"\n\n";
//                        }
//                        nutritextView.setText(info);
//                    }
//                });
//    }



}
