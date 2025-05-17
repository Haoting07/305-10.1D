package com.example.a61d.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.utils.PreferenceManager;

public class MockPaymentActivity extends AppCompatActivity {

    private EditText cardholderInput, cardNumberInput, expiryInput, cvcInput;
    private TextView paymentAmountText;
    private Button payButton;

    private String level;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_payment);

        // 接收从 UpgradeActivity 传过来的套餐信息
        level = getIntent().getStringExtra("level");
        amount = getIntent().getStringExtra("amount");

        // 绑定视图组件
        cardholderInput = findViewById(R.id.cardholderNameInput);
        cardNumberInput = findViewById(R.id.cardNumberInput);
        expiryInput = findViewById(R.id.expiryDateInput);
        cvcInput = findViewById(R.id.cvcInput);
        paymentAmountText = findViewById(R.id.paymentAmount);
        payButton = findViewById(R.id.payButton);

        paymentAmountText.setText("Amount: " + amount);

        // 点击 Pay 按钮事件
        payButton.setOnClickListener(v -> {
            String cardholder = cardholderInput.getText().toString().trim();
            String cardNumber = cardNumberInput.getText().toString().trim();
            String expiry = expiryInput.getText().toString().trim();
            String cvc = cvcInput.getText().toString().trim();

            // 简单校验
            if (cardholder.isEmpty() || cardNumber.length() < 12 || expiry.length() < 4 || cvc.length() < 3) {
                Toast.makeText(this, "Please fill all fields with valid card info.", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ 模拟保存升级等级
            PreferenceManager.saveUserAccountLevel(this, level);

            // 弹出付款成功提示
            new AlertDialog.Builder(this)
                    .setTitle("Payment Successful")
                    .setMessage("You paid " + amount + " with card ending in " + getCardSuffix(cardNumber)
                            + ".\n\nAccount upgraded to: " + level)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        });
    }

    // 取卡号后四位用于展示
    private String getCardSuffix(String cardNumber) {
        if (cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        } else {
            return "****";
        }
    }
}


