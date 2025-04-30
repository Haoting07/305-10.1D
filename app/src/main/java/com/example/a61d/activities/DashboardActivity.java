package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.utils.PreferenceManager;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button startQuizBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        startQuizBtn = findViewById(R.id.startButton);
        logoutBtn = findViewById(R.id.logoutButton); // 你需要在 XML 中加上对应按钮

        String username = PreferenceManager.getCurrentUser(this);
        if (username != null) {
            welcomeText.setText("Welcome, " + username);
        } else {
            welcomeText.setText("Welcome!");
        }

        startQuizBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, QuizActivity.class));
        });

        logoutBtn.setOnClickListener(v -> {
            PreferenceManager.clearSession(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}

