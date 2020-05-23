package com.example.healthfull.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodSearchResultsAdapter extends RecyclerView.Adapter<FoodSearchResultsAdapter.ViewHolder> {

    private static final String TAG = "FoodSearchResults";

    private FoodSearchResults foodSearchResults;

    private View.OnClickListener onAddClickedListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private TextView nameView;
        private Button addButton;
        public ViewHolder(ConstraintLayout l) {
            super(l);
            layout = l;
            nameView = (TextView) layout.getChildAt(0);
            addButton = (Button) layout.getChildAt(1);
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

        public Button getAddButton() {
            return addButton;
        }

        public TextView getNameView() {
            return nameView;
        }
    }

    public FoodSearchResultsAdapter(FoodSearchResults foodSearchResults, View.OnClickListener onAddClickedListener) {
        this.foodSearchResults = foodSearchResults;
        this.onAddClickedListener = onAddClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate new view
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_searchresult, parent, false);

        ViewHolder vh = new ViewHolder(layout);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getLayout().setTag(foodSearchResults.get(position).getId());

        holder.getNameView().setText(foodSearchResults.get(position).getName());

        holder.getAddButton().setTag(foodSearchResults.get(position).getId());
        holder.getAddButton().setOnClickListener(onAddClickedListener);
    }

    @Override
    public int getItemCount() {
        return foodSearchResults.size();
    }
}
