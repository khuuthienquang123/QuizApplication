package com.example.quizapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Category;
import com.example.quizapplication.database.CategoryDao;

import java.util.List;

public class AdminManageActivityCategories extends AppCompatActivity {
    private EditText categoryNameEditText;
    private ListView categoriesListView;
    private CategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_management_categories);

        categoryNameEditText = findViewById(R.id.category_name_edit_text);
        Button saveCategoryButton = findViewById(R.id.save_category_button);
        categoriesListView = findViewById(R.id.categories_list_view);
        categoryDao = new CategoryDao(this);

        saveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = categoryNameEditText.getText().toString();
                if (!categoryName.isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryName);
                    categoryDao.addCategory(category);
                    Toast.makeText(AdminManageActivityCategories.this, "Category added", Toast.LENGTH_SHORT).show();
                    loadCategories();
                } else {
                    Toast.makeText(AdminManageActivityCategories.this, "Category name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoriesListView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                Intent intent = new Intent(AdminManageActivityCategories.this, AdminManagementActivityQuizzes.class);
                intent.putExtra("categoryId", selectedCategory.getId());
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(AdminManageActivityCategories.this, "Error handling item click", Toast.LENGTH_SHORT).show();
            }
        });

        loadCategories();
    }

    private void loadCategories() {
        try {
            List<Category> categoryList = categoryDao.getCategories();
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
            categoriesListView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Error loading categories", Toast.LENGTH_SHORT).show();
        }
    }
}
