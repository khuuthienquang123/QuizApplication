package com.example.quizapplication.activities.user;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Category;
import com.example.quizapplication.database.CategoryDao;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        RecyclerView categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryDao categoryDao = new CategoryDao(this);
        List<Category> categories = categoryDao.getCategories();

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
