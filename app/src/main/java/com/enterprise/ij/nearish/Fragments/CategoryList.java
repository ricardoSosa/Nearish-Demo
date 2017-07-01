package com.enterprise.ij.nearish.Fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.enterprise.ij.nearish.Activities.MainActivity;
import com.enterprise.ij.nearish.Adapters.CategoryAdapter;
import com.enterprise.ij.nearish.Models.Category;
import com.enterprise.ij.nearish.Other.GetUserCategories;
import com.enterprise.ij.nearish.Other.ServiceHandler;
import com.enterprise.ij.nearish.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryList extends Fragment {

    ListView categoriesList;
    ArrayList<Category> categories = new ArrayList<>();
    String userCategories = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_categories_list, container, false);
        categoriesList = (ListView) rootView.findViewById(R.id.categorieList);

        categories.add(new Category("594c368633cbb6eed6f364da", "Restaurant", R.drawable.red_marker));
        categories.add(new Category("594c4a504362de3d8fae464f", "Cafe",R.drawable.blue_marker));
        categories.add(new Category("5956fe667d2bf938c292e0b8", "Bakery",R.drawable.green_marker));
        categories.add(new Category("5956fefa7d2bf938c292e0bd", "Bar",R.drawable.orange_marker));
        categories.add(new Category("5956fec37d2bf938c292e0ba", "Night club",R.drawable.black_marker));

        GetUserCategories getUserCategories = new GetUserCategories();
        Object[] o = new Object[1];
        o[0] = ((MainActivity) getActivity()).getUserId();
        try {
            userCategories = getUserCategories.execute(o).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            Gson gson = new Gson();
            Category[] catsArray = gson.fromJson(userCategories, Category[].class);
            ArrayList<Category> cats = new ArrayList<>();
            Collections.addAll(cats, catsArray);

            final CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categories, cats);

            categoriesList.setAdapter(categoryAdapter);

            FloatingActionButton saveButton = (FloatingActionButton) rootView.findViewById(R.id.saveButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> types = new ArrayList<String>();
                    for (int i = 0; i < 5; i++){
                        Category cat = (Category) categoryAdapter.getItem(i);
                        if (cat.getChecked() == true) {
                            types.add(cat.getId());
                        }
                    }
                    String[] t = (String[]) types.toArray();
                    ServiceHandler serviceHandler = new ServiceHandler();
                    serviceHandler.executePatchCategories(((MainActivity) getActivity()).getUserId(), t);
                }
            });
        }

        return rootView;
    }
}