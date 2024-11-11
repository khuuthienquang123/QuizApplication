package com.example.quizapplication.activities.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Answer;
import com.example.quizapplication.database.AnswerDao;

import java.util.List;

public class AdminManagementActivityAnswers extends AppCompatActivity {
    private ListView answerListView;
    private AnswerDao answerDao;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_activity_management_answers);

        answerListView = findViewById(R.id.answers_list_view);
        answerDao = new AnswerDao(this);

        int quizID = getIntent().getIntExtra("quizId", -1);
        if (quizID != -1){
            loadAnswers(quizID);
        }else {
            Toast.makeText(this, "Quiz ID not found", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadAnswers(int quizId) {
        List<Answer> answers = answerDao.getAnswersByQuizId(quizId);
        ArrayAdapter<Answer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, answers);
        answerListView.setAdapter(adapter);
    }

}
