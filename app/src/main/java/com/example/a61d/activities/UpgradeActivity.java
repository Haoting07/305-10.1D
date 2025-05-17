package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.utils.PreferenceManager;

public class UpgradeActivity extends AppCompatActivity {

    private TextView upgradeStatusText;
    private Button basicBtn, proBtn, plusBtn;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        upgradeStatusText = findViewById(R.id.upgradeStatusText);
        basicBtn = findViewById(R.id.basicBtn);
        proBtn = findViewById(R.id.proBtn);
        plusBtn = findViewById(R.id.plusBtn);

        currentUser = PreferenceManager.getCurrentUser(this);
        refreshAccountLevelDisplay();

        // 点击每个按钮跳转模拟付款页面（传递套餐名和金额）
        basicBtn.setOnClickListener(v -> openMockPayment("Basic", "$3.00"));
        proBtn.setOnClickListener(v -> openMockPayment("Pro", "$5.00"));
        plusBtn.setOnClickListener(v -> openMockPayment("Plus", "$8.00"));
    }

    // 页面返回时刷新显示
    @Override
    protected void onResume() {
        super.onResume();
        refreshAccountLevelDisplay();
    }

    // 显示当前用户账户等级
    private void refreshAccountLevelDisplay() {
        String level = PreferenceManager.getUserAccountLevel(this);
        upgradeStatusText.setText("Current Account: " + level);
    }

    // 打开模拟付款界面，传递套餐信息
    private void openMockPayment(String level, String amount) {
        Intent intent = new Intent(this, MockPaymentActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("amount", amount);
        startActivity(intent);
    }
}


