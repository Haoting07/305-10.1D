package com.example.a61d.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.database.HistoryPayload;
import com.example.a61d.database.QuizHistory;
import com.example.a61d.models.Quiz;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class HistoryDetailActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        container = findViewById(R.id.historyDetailContainer);
        QuizHistory history = (QuizHistory) getIntent().getSerializableExtra("record");

        if (history != null) {
            Gson gson = new Gson();
            HistoryPayload payload = gson.fromJson(history.getFullJson(), HistoryPayload.class);
            List<Quiz> quizList = payload.quizList;
            Map<Integer, String> userAnswers = payload.userAnswers;

            for (int i = 0; i < quizList.size(); i++) {
                Quiz quiz = quizList.get(i);
                String userAns = userAnswers.get(i);
                String correctAns = quiz.getCorrectAnswer();

                TextView textView = new TextView(this);
                textView.setText("Q" + (i + 1) + ": " + quiz.getQuestion() + "\nYour Answer: " + userAns + "\nCorrect Answer: " + correctAns);
                textView.setPadding(16, 24, 16, 24);
                textView.setTextSize(16f);
                if (userAns != null && userAns.equalsIgnoreCase(correctAns)) {
                    textView.setTextColor(Color.parseColor("#388E3C")); // green
                } else {
                    textView.setTextColor(Color.RED); // wrong
                }
                container.addView(textView);
            }
        }
    }
}
