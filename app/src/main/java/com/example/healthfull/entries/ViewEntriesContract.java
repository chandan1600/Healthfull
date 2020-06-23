package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.util.MVPContract;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public interface ViewEntriesContract extends MVPContract {

    interface View {
        void onLoadSuccess(List<FoodEntry> entries);
        void setEntriesViewAdapter(RecyclerView.Adapter adapter);
        void showProgressBar(boolean enabled);
    }

    interface Presenter {
        void loadEntries(DocumentReference userRef);
    }

    interface Interactor {
        void performFirebaseEntriesLoad(DocumentReference userRef);
    }

    interface onLoadListener {
        void onLoadSuccess(List<FoodEntry> entries);
        void onLoadFailure(String message);
    }
}
