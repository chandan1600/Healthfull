package com.example.healthfull.search_nutri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthfull.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<NutrientList> nutrientList = null;
    private ArrayList<NutrientList> arraylist;

    public ListViewAdapter(Context context, ArrayList<NutrientList> nutrientList) {
        mContext = context;
        this.nutrientList = nutrientList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<NutrientList>();
        this.arraylist.addAll(nutrientList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return nutrientList.size();
    }

    @Override
    public NutrientList getItem(int position) {
        return nutrientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(nutrientList.get(position).getNutrientName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        nutrientList.clear();
        if (charText.length() == 0) {
            nutrientList.addAll(arraylist);
        } else {
            for (NutrientList wp : arraylist) {
                if (wp.getNutrientName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    nutrientList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}