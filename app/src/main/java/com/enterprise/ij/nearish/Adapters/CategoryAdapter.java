package com.enterprise.ij.nearish.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.enterprise.ij.nearish.Models.Category;
import com.enterprise.ij.nearish.R;

import java.util.ArrayList;

/**
 * Created by juan on 27/06/2017.
 */

public class CategoryAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Category> categories;

    public CategoryAdapter(Activity activity, ArrayList<Category> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    public void clear() {
        categories.clear();
    }

    public void addAll(ArrayList<Category> newCategories) {
        for (int i = 0; i < newCategories.size(); i++) {
            categories.add(newCategories.get(i));
        }
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.category_item, null);
        }

        Category dir = categories.get(position);

        ImageView imagen = (ImageView) v.findViewById(R.id.categoryImage);
        imagen.setImageDrawable(dir.getImage());

        return v;
    }
}