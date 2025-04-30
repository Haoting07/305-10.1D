package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a61d.R;
import com.example.a61d.adapters.ResultAdapter;
import com.example.a61d.models.Quiz;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView resultRecyclerView;
    private TextView finalScoreText;
    private Button backToDashboardButton;
    private ArrayList<Quiz> quizList;
    private HashMap<Integer, String> userAnswers;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 绑定 UI 元素
        resultRecyclerView = findViewById(R.id.resultRecyclerView);
        finalScoreText = findViewById(R.id.finalScoreText);
        backToDashboardButton = findViewById(R.id.backToDashboardButton);

        // 获取传递过来的数据
        quizList = (ArrayList<Quiz>) getIntent().getSerializableExtra("quizList");
        userAnswers = (HashMap<Integer, String>) getIntent().getSerializableExtra("userAnswers");
        score = getIntent().getIntExtra("score", 0);

        // 显示分数
        finalScoreText.setText("Your Score: " + score + "/" + quizList.size());

        // 设置 RecyclerView
        ResultAdapter adapter = new ResultAdapter(quizList, userAnswers);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(adapter);

        // 返回按钮逻辑
        backToDashboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // 防止回退时回到 Result 页面
        });
    }
}
