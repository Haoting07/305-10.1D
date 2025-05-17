package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.utils.PreferenceManager;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText, accountLevelText;
    private Button startQuizBtn, logoutBtn, profileBtn, historyBtn, upgradeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        accountLevelText = findViewById(R.id.accountLevelText);
        startQuizBtn = findViewById(R.id.startButton);
        logoutBtn = findViewById(R.id.logoutButton);
        profileBtn = findViewById(R.id.profileBtn);
        historyBtn = findViewById(R.id.historyBtn);
        upgradeBtn = findViewById(R.id.upgradeBtn);

        String username = PreferenceManager.getCurrentUser(this);
        welcomeText.setText("Welcome, " + username);

        updateAccountLevel();

        startQuizBtn.setOnClickListener(v ->
                startActivity(new Intent(this, QuizActivity.class)));

        logoutBtn.setOnClickListener(v -> {
            PreferenceManager.clearSession(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        profileBtn.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        historyBtn.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));

        upgradeBtn.setOnClickListener(v ->
                startActivity(new Intent(this, UpgradeActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAccountLevel();
    }

    private void updateAccountLevel() {
        String level = PreferenceManager.getUserAccountLevel(this);
        accountLevelText.setText("Account Level: " + level);
    }
}



