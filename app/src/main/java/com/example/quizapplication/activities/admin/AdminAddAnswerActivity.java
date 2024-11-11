package com.example.quizapplication.activities.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Answer;
import com.example.quizapplication.database.AnswerDao;

import java.util.ArrayList;
import java.util.List;

public class AdminAddAnswerActivity extends AppCompatActivity {
    private EditText answerTextEdit1, answerTextEdit2, answerTextEdit3, answerTextEdit4;
    private RadioGroup correctAnswerRadioGroup;
    private AnswerDao answerDao;
    private int quizId;

    private List<Answer> existingAnswers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_answer);

        answerTextEdit1 = findViewById(R.id.answer_text_edit_text_1);
        answerTextEdit2 = findViewById(R.id.answer_text_edit_text_2);
        answerTextEdit3 = findViewById(R.id.answer_text_edit_text_3);
        answerTextEdit4 = findViewById(R.id.answer_text_edit_text_4);

        correctAnswerRadioGroup = findViewById(R.id.correct_answer_radio_group);
        Button saveAnswerButton = findViewById(R.id.save_answer_button);
        answerDao = new AnswerDao(this);

        quizId = getIntent().getIntExtra("quizId", -1);

        loadExistingAnswers();

        // Log the contents of the answers table
        answerDao.logAnswersTable();

        saveAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();
            }
        });
    }

    private void loadExistingAnswers() {
        existingAnswers = answerDao.getAnswersByQuizId(quizId);
        if (existingAnswers == null) {
            existingAnswers = new ArrayList<>();
        }

        if (!existingAnswers.isEmpty()) {
            Log.d("AdminAddAnswerActivity", "Existing answers found: " + existingAnswers.size());
            if (!existingAnswers.isEmpty()) answerTextEdit1.setText(existingAnswers.get(0).getText());
            if (existingAnswers.size() > 1) answerTextEdit2.setText(existingAnswers.get(1).getText());
            if (existingAnswers.size() > 2) answerTextEdit3.setText(existingAnswers.get(2).getText());
            if (existingAnswers.size() > 3) answerTextEdit4.setText(existingAnswers.get(3).getText());

            for (int i = 0; i < existingAnswers.size(); i++) {
                if (existingAnswers.get(i).isCorrect()) {
                    switch (i) {
                        case 0:
                            correctAnswerRadioGroup.check(R.id.correct_answer_radio_button_1);
                            break;
                        case 1:
                            correctAnswerRadioGroup.check(R.id.correct_answer_radio_button_2);
                            break;
                        case 2:
                            correctAnswerRadioGroup.check(R.id.correct_answer_radio_button_3);
                            break;
                        case 3:
                            correctAnswerRadioGroup.check(R.id.correct_answer_radio_button_4);
                            break;
                    }
                }
            }
        } else {
            Log.d("AdminAddAnswerActivity", "No existing answers found for quizId: " + quizId);
        }
    }

    private void saveAnswer(){
        List<Answer> answers = existingAnswers;

        if(answers.size() < 4){
            for(int i = answers.size(); i< 4; i++){
                answers.add(new Answer());
            }
        }

        answers.get(0).setText(answerTextEdit1.getText().toString());
        answers.get(0).setCorrect(correctAnswerRadioGroup.getCheckedRadioButtonId() == R.id.correct_answer_radio_button_1);
        answers.get(1).setText(answerTextEdit2.getText().toString());
        answers.get(1).setCorrect(correctAnswerRadioGroup.getCheckedRadioButtonId() == R.id.correct_answer_radio_button_2);
        answers.get(2).setText(answerTextEdit3.getText().toString());
        answers.get(2).setCorrect(correctAnswerRadioGroup.getCheckedRadioButtonId() == R.id.correct_answer_radio_button_3);
        answers.get(3).setText(answerTextEdit4.getText().toString());
        answers.get(3).setCorrect(correctAnswerRadioGroup.getCheckedRadioButtonId() == R.id.correct_answer_radio_button_4);

        for (Answer answer : answers) {
            if (!answer.getText().isEmpty()) {
                if (answer.getId() == 0) {
                    answer.setQuizId(quizId);
                    answerDao.addAnswer(answer);
                    Log.d("AdminAddAnswerActivity", "Answer added: " + answer.getText());
                } else {
                    answerDao.updateAnswer(answer);
                    Log.d("AdminAddAnswerActivity", "Answer updated: " + answer.getText());
                }
            }
        }

        Toast.makeText(AdminAddAnswerActivity.this, "Answers saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
