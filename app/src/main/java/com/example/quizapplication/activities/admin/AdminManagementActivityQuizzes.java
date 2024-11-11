package com.example.quizapplication.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Quiz;
import com.example.quizapplication.database.QuizDao;

import java.util.List;

public class AdminManagementActivityQuizzes extends AppCompatActivity {
    private EditText quizNameEditText;
    private ListView quizzesListView;
    private QuizDao quizDao;
    private int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_management_quizzes);

        quizNameEditText = findViewById(R.id.quiz_name_edit_text);
        quizzesListView = findViewById(R.id.quizzes_list_view);
        Button SaveQuizButton = findViewById(R.id.save_quiz_button);
        quizDao = new QuizDao(this);

        categoryID = getIntent().getIntExtra("categoryId", -1);

        if (categoryID != -1) {
            loadQuizzes(categoryID);
        }else {
            Toast.makeText(this, "Category ID not found", Toast.LENGTH_SHORT).show();
        }

        SaveQuizButton.setOnClickListener(v -> {
            String quizName = quizNameEditText.getText().toString();
            if (!quizName.isEmpty()) {
                Quiz quiz = new Quiz();
                quiz.setQuestion(quizName);
                quiz.setCategoryId(categoryID);
                quizDao.addQuiz(quiz);
                Toast.makeText(AdminManagementActivityQuizzes.this, "Quiz added to", Toast.LENGTH_SHORT).show();
                loadQuizzes(categoryID);
            } else {
                Toast.makeText(AdminManagementActivityQuizzes.this, "Quiz name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        quizzesListView.setOnItemClickListener((parent, view, position, id) -> {
            Quiz selectedQuiz = (Quiz) parent.getItemAtPosition(position);
            Intent intent = new Intent(AdminManagementActivityQuizzes.this, AdminAddAnswerActivity.class);
            intent.putExtra("quizId", selectedQuiz.getId());
            startActivity(intent);
        });

        loadQuizzes(categoryID);
    }

    private void loadQuizzes(int categoryId) {
        List<Quiz> quizzes = quizDao.getQuizzesByCategoryId(categoryId);
        ArrayAdapter<Quiz> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quizzes);
        quizzesListView.setAdapter(adapter);
    }
}
