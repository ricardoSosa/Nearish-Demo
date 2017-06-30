package com.enterprise.ij.nearish.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.enterprise.ij.nearish.Adapters.CategoryAdapter;
import com.enterprise.ij.nearish.Models.Category;
import com.enterprise.ij.nearish.R;

import java.util.ArrayList;

public class CategoryList extends Fragment {

    ListView categoriesList;
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_categories_list, container, false);
        categoriesList = (ListView) rootView.findViewById(R.id.categorieList);

        categories.add(new Category("Comida rápida", ContextCompat.getDrawable(getActivity(),R.drawable.logo)));

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),categories);

        categoriesList.setAdapter(categoryAdapter);

        return rootView;
    }
}