package com.example.healthfull.gallery;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryGridView extends AppCompatActivity {
    RecyclerView datalist;
    List<Integer> images;
    List<String> titles;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);

        datalist = findViewById(R.id.datalistRecyclerView);
        images = new ArrayList<>();
        titles = new ArrayList<>();

        titles.add("");
        titles.add("");
        titles.add("");

        images.add(R.drawable.pie);
        images.add(R.drawable.juice);
        images.add(R.drawable.omelette);

        adapter = new Adapter(this, titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(adapter);

    }


}
