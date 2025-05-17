package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    private LinearLayout checkboxContainer;
    private Button finishButton;

    private String[] interests = {
            "Math", "Science", "History", "Web Development",
            "AI", "Mobile Apps", "Design", "Geography",
            "Health", "Languages"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        checkboxContainer = findViewById(R.id.checkboxContainer);
        finishButton = findViewById(R.id.finishButton);

        for (String interest : interests) {
            CheckBox cb = new CheckBox(this);
            cb.setText(interest);
            checkboxContainer.addView(cb);
        }

        finishButton.setOnClickListener(v -> {
            List<String> selected = new ArrayList<>();
            for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                CheckBox cb = (CheckBox) checkboxContainer.getChildAt(i);
                if (cb.isChecked()) {
                    selected.add(cb.getText().toString());
                }
            }

            if (selected.size() == 0) {
                Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show();
            } else if (selected.size() > 10) {
                Toast.makeText(this, "Please select up to 10 interests", Toast.LENGTH_SHORT).show();
            } else {
                // 这里可扩展保存兴趣逻辑
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            }
        });
    }
}
