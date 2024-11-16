package com.example.quizapplication.activities.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.model.Answer;
import com.example.quizapplication.activities.model.Quiz;
import com.example.quizapplication.database.AnswerDao;
import com.example.quizapplication.database.QuizDao;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private List<Answer> answers;
    private List<Quiz> quizzes;
    private int currentQuizIndex = 0;
    private TextView questionText;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private TextView timer;

    private CountDownTimer questionTimer;
    private CountDownTimer nextQuestionTimer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.question_text_view);
        option1 = findViewById(R.id.option1_button);
        option2 = findViewById(R.id.option2_button);
        option3 = findViewById(R.id.option3_button);
        option4 = findViewById(R.id.option4_button);
        timer = findViewById(R.id.timer_text_view);

        // Set the question text
        int categoryId = getIntent().getIntExtra("categoryId", -1);
        Log.d(TAG, "Received categoryId: " + categoryId);

        if(categoryId != -1){
            QuizDao quizDao = new QuizDao(this);
            quizzes = quizDao.getQuizzesByCategoryId(categoryId);

            if (quizzes.isEmpty()){
                questionText.setText("No questions found for this quiz");
                Log.e("QuizActivity", "No questions found for this quiz");
            } else {
                Log.d(TAG, "Quizzes retrieved: " + quizzes.size());
                loadQuestion();
            }
        }else {
            questionText.setText("No quiz found");
            Log.e("QuizActivity", "No quiz found");
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestion() {
        Quiz quiz = quizzes.get(currentQuizIndex);
        questionText.setText(quiz.getQuestion());

        AnswerDao answerDao = new AnswerDao(this);
        answers = answerDao.getAnswersByQuizId(quiz.getId());
        Log.d(TAG, "Answers retrieved: " + answers.size());

        if (answers.size() >= 4) {
            option1.setText(answers.get(0).getText());
            option2.setText(answers.get(1).getText());
            option3.setText(answers.get(2).getText());
            option4.setText(answers.get(3).getText());

            Log.d(TAG, "Answer 1: " + answers.get(0).getText());
            Log.d(TAG, "Answer 2: " + answers.get(1).getText());
            Log.d(TAG, "Answer 3: " + answers.get(2).getText());
            Log.d(TAG, "Answer 4: " + answers.get(3).getText());

            option1.setOnClickListener(v -> {
                evaluateAnswer(0);
                startNextQuestionTimer();
            });

            option2.setOnClickListener(v -> {
                evaluateAnswer(1);
                startNextQuestionTimer();
            });

            option3.setOnClickListener(v -> {
                evaluateAnswer(2);
                startNextQuestionTimer();
            });

            option4.setOnClickListener(v -> {
                evaluateAnswer(3);
                startNextQuestionTimer();
            });

        } else {
            questionText.setText("Not enough answers for this question");
            Log.e("QuizActivity", "Not enough answers for this question");
        }
    }

    private void startQuestionTimer() {
        questionTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timer.setText("0");
                questionText.setText("Time's up! Wrong answer.");
                for (Answer answer : answers) {
                    if (answer.isCorrect()) {
                        questionText.append("\nCorrect answer: " + answer.getText());
                        break;
                    }
                }
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);
                startNextQuestionTimer();
            }
        }.start();
    }

    private void evaluateAnswer(int answerIndex) {
        if (questionTimer != null) {
            questionTimer.cancel();
        }

        if (answers.get(answerIndex).isCorrect()) {
            questionText.setText("Correct answer!");

        } else {
            questionText.setText("Wrong answer.");
            for (Answer answer : answers) {
                if (answer.isCorrect()) {
                    questionText.append("\nCorrect answer: " + answer.getText());
                    break;
                }
            }
        }
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);

        startNextQuestionTimer();
    }

    private void startNextQuestionTimer() {
        questionTimer = new CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("Next question in: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                currentQuizIndex++;
                if (currentQuizIndex < quizzes.size()) {
                    loadQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, CongratulationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (questionTimer != null) {
            questionTimer.cancel();
        }
        if (nextQuestionTimer != null) {
            nextQuestionTimer.cancel();
        }
    }
}
