package com.example.healthfull.recommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

/**
 *  @author Chandan Aulakh
 *  Recommendation class retrieves a random food from the database to offer as a recommendation
 *  to the user for a food to eat.
 */

public class Recommendation extends AppCompatActivity {

    private static String random;
    private TextView recText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recRef = db.collection("food");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        recText = findViewById(R.id.recText);
    }

    //random number is generated
    public String randomNum(){
        Random rnd = new Random();
        int r1 = rnd.nextInt(4)+1;
        random = String.valueOf(r1);
        return random;
    }

    /**
     * retrieves recommendation from the database to offer to the user using a snapshot
     * places info into recommendation object, where it is displayed in text view
     * @param view
     */
    public void getRec(View view){
        randomNum();
        recRef.whereEqualTo("id", random)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String display = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            RecObject recommend = documentSnapshot.toObject(RecObject.class);

                            String name = recommend.getName();
                            String protein = recommend.getProtein();
                            String carb = recommend.getCarbohydrate();
                            String fat = recommend.getFat();
                            String serving = recommend.getServing_size();

                            display += "name: "+name+"\nprotein: "+protein+"\ncarbohydrate: "+
                                    carb+"\nfat: "+fat+"\nserving size: "+serving;
                        }
                        recText.setText(display);
                        Toast.makeText(Recommendation.this,"reccomendation loaded",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}




