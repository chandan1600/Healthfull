package com.example.healthfull.DailyTarget;

import android.os.Bundle;
import android.util.Log;
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

/**
 * @author Chandan Aulakh
 * Daily Target class adds food and water target to database and retrieves targets from database
 */
public class DailyTarget extends AppCompatActivity {

    private static final String TAG = "DailyTarget";//to log errors

    //variables for xml components
    private EditText editTextWaterTarget;
    private EditText editTextFoodTarget;
    private TextView textViewTarget;

    //Firebase connection made and reference to "users" collection established
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference addTargetDoc = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_target);
        //connecting text view and edit text to variables
        editTextWaterTarget = findViewById(R.id.edit_textWaterTarget);
        editTextFoodTarget = findViewById(R.id.edit_textFoodTarget);
        textViewTarget = findViewById(R.id.textView_Targets);
    }

    /**
     * retrieves target info from text boxes and is processed into target object class
     * this is then stored in a sub-collection of the user. A toast is released.
     * @param view
     */
    public void addFoodWaterTarget(View view){
        String WaterTarget = editTextWaterTarget.getText().toString();
        String FoodTarget = editTextFoodTarget.getText().toString() + " calories";

        TargetObject target = new TargetObject(WaterTarget,FoodTarget);

        addTargetDoc.document("u1").collection("targets").add(target);
        Toast.makeText(this, "target saved", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"successful");
    }

    /**
     * load target performs snapshot of all targets in targets sub-collection for a user
     * returns fields and assigns them to the target object which is then displayed in
     * textView.
     * @param view
     */
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






