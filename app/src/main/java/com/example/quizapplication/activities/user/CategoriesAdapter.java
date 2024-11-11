package com.example.quizapplication.activities.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    Context context;
    private final List<Category> categories;
    public CategoriesAdapter(Context context, List<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryNameTextView.setText(category.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WaitingActivity.class);
            intent.putExtra("categoryId", category.getId());
            Log.d("QuizActivity", "Sending categoryId: " + category.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.category_name_text_view);
        }
    }
}
