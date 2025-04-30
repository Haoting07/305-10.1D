package com.example.a61d.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a61d.R;
import com.example.a61d.adapters.QuizAdapter;
import com.example.a61d.models.Quiz;
import com.example.a61d.models.QuizResponse;
import com.example.a61d.network.ApiClient;
import com.example.a61d.network.ApiService;
import com.example.a61d.utils.PreferenceManager;


import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button submitButton;
    private ProgressBar loadingSpinner;

    private QuizAdapter quizAdapter;
    private List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        recyclerView = findViewById(R.id.quizRecyclerView);
        submitButton = findViewById(R.id.submitButton);
        loadingSpinner = findViewById(R.id.loadingSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadingSpinner.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);

        String topic = PreferenceManager.getUserTopic(this);
        loadQuiz(topic);


    }

    private void loadQuiz(String topic) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getQuiz(topic).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                loadingSpinner.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    quizList = response.body().quiz;
                    quizAdapter = new QuizAdapter(quizList);
                    recyclerView.setAdapter(quizAdapter);
                    submitButton.setEnabled(true);
                } else {
                    Toast.makeText(QuizActivity.this, "Failed to load quiz", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                loadingSpinner.setVisibility(View.GONE);
                Toast.makeText(QuizActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        submitButton.setOnClickListener(v -> {
            int score = quizAdapter.calculateScore();

            // 跳转到结果页并传值
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("quizList", (Serializable) quizList);
            intent.putExtra("userAnswers", (Serializable) quizAdapter.getUserAnswers());
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        });
    }
}



