package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.database.HistoryDatabaseHelper;
import com.example.a61d.database.HistoryPayload;
import com.example.a61d.database.QuizHistory;
import com.example.a61d.models.Quiz;
import com.example.a61d.utils.PreferenceManager;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText, emailText, totalQuestionsText, correctAnswersText, incorrectAnswersText;
    private Button shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 绑定视图
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        totalQuestionsText = findViewById(R.id.totalQuestions);
        correctAnswersText = findViewById(R.id.correctAnswers);
        incorrectAnswersText = findViewById(R.id.incorrectAnswers);
        shareBtn = findViewById(R.id.shareButton);

        // 加载用户信息
        String username = PreferenceManager.getCurrentUser(this);
        usernameText.setText(username);
        emailText.setText(username + "@example.com");

        // 加载答题统计
        loadStats();

        // 点击分享跳转
        shareBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
        });

        // 返回按钮（可选）
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStats(); // 每次进入页面刷新
    }

    private void loadStats() {
        int total = 0, correct = 0;

        List<QuizHistory> allRecords = new HistoryDatabaseHelper(this).getAllHistory();
        Gson gson = new Gson();

        for (QuizHistory record : allRecords) {
            String json = record.getFullJson();
            if (json == null || json.isEmpty()) continue;

            HistoryPayload payload = gson.fromJson(json, HistoryPayload.class);
            List<Quiz> quizList = payload.quizList;
            Map<Integer, String> userAnswers = payload.userAnswers;

            total += quizList.size();

            for (int i = 0; i < quizList.size(); i++) {
                Quiz quiz = quizList.get(i);
                String correctAns = quiz.getCorrectAnswer();
                String userAns = userAnswers.get(i);

                if (correctAns != null && correctAns.equalsIgnoreCase(userAns)) {
                    correct++;
                }
            }
        }

        int incorrect = total - correct;

        totalQuestionsText.setText(String.valueOf(total));
        correctAnswersText.setText(String.valueOf(correct));
        incorrectAnswersText.setText(String.valueOf(incorrect));
    }
}


