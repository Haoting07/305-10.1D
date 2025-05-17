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
import com.example.a61d.utils.PreferenceManager;
import com.example.a61d.database.HistoryDatabaseHelper;
import com.example.a61d.database.HistoryPayload;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

        // 获取传递的数据
        quizList = (ArrayList<Quiz>) getIntent().getSerializableExtra("quizList");
        userAnswers = (HashMap<Integer, String>) getIntent().getSerializableExtra("userAnswers");
        score = getIntent().getIntExtra("score", 0);

        // 显示分数
        finalScoreText.setText("Your Score: " + score + "/" + quizList.size());

        // 设置 RecyclerView 结果列表
        ResultAdapter adapter = new ResultAdapter(quizList, userAnswers);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(adapter);

        // 准备保存历史记录数据
        String username = PreferenceManager.getCurrentUser(this);
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Gson gson = new Gson();
        HistoryPayload payload = new HistoryPayload(quizList, userAnswers);
        String fullJson = gson.toJson(payload); // 将答题内容转换为 JSON 字符串

        // 保存到本地数据库
        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(this);
        dbHelper.insertHistory(username, dateTime, score, fullJson);

        // 返回主页按钮
        backToDashboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // 防止返回历史页
        });
    }
}


