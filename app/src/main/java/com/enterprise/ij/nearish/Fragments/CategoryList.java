package com.enterprise.ij.nearish.Fragments;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.enterprise.ij.nearish.Adapters.CategoryAdapter;
import com.enterprise.ij.nearish.Models.Category;
import com.enterprise.ij.nearish.R;

import java.util.ArrayList;

public class CategoryList extends AppCompatActivity {

    ListView categoriesList;
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        categoriesList = (ListView) findViewById(R.id.categorieList);

        categories.add(new Category("Comida rápida", ContextCompat.getDrawable(this,R.drawable.logo)));
        categories.add(new Category("Comida china", ContextCompat.getDrawable(this,R.drawable.logo)));
        categories.add(new Category("Comida mexicana", ContextCompat.getDrawable(this,R.drawable.logo)));
        categories.add(new Category("Comida india", ContextCompat.getDrawable(this,R.drawable.logo)));

        CategoryAdapter categoryAdapter = new CategoryAdapter(this,categories);

        categoriesList.setAdapter(categoryAdapter);

    }
}