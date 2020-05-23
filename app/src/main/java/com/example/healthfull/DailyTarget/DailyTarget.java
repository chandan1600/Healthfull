package com.example.healthfull.DailyTarget;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DailyTarget extends AppCompatActivity {

    private static final String TAG = "DailyTarget";//to log errors

    private EditText editTextWaterTarget;//water target entry
    private EditText editTextFoodTarget;//food target entry
    private TextView textViewTarget;//display for targets

    //connection to fire base and calling the targets sub-collection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference addTargetDoc = db.collection("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_target);
        //linking id variable
        editTextWaterTarget = findViewById(R.id.edit_textWaterTarget);
        editTextFoodTarget = findViewById(R.id.edit_textFoodTarget);
        textViewTarget = findViewById(R.id.textView_Targets);
    }

    //adds food and water target to "target" sub-collection as document with fields
    public void addFoodWaterTarget(View view){
        String WaterTarget = editTextWaterTarget.getText().toString();
        String FoodTarget = editTextFoodTarget.getText().toString() + " calories";

        TargetObject target = new TargetObject(WaterTarget,FoodTarget);

        addTargetDoc.document("u1").collection("targets").add(target);
        Toast.makeText(this, "target saved", Toast.LENGTH_SHORT).show();
    }

    //loads all previous targets made
    public void loadTarget(View view){
        addTargetDoc.document("u1")
                .collection("targets")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       String info = "";

                       for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                           TargetObject target = documentSnapshots.toObject(TargetObject.class);

                           String water = target.getWaterTarget();
                           String food = target.getFoodTarget();

                           info += "water target: "+water+"\nfood target: "+food+"\n\n";
                       }
                        textViewTarget.setText(info);
                        Toast.makeText(DailyTarget.this, "Targets Loaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}



