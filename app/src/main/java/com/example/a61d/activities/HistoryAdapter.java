package com.example.a61d.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a61d.R;
import com.example.a61d.database.HistoryPayload;
import com.example.a61d.database.QuizHistory;
import com.example.a61d.models.Quiz;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<QuizHistory> historyList;

    public HistoryAdapter(Context context, List<QuizHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        QuizHistory history = historyList.get(position);
        holder.historyDate.setText(history.getDate());

        // 动态解析答题数量和得分
        Gson gson = new Gson();
        int total = 0;
        int correct = 0;

        try {
            HistoryPayload payload = gson.fromJson(history.getFullJson(), HistoryPayload.class);
            List<Quiz> quizList = payload.quizList;
            Map<Integer, String> userAnswers = payload.userAnswers;

            total = quizList.size();
            for (int i = 0; i < total; i++) {
                Quiz quiz = quizList.get(i);
                String userAnswer = userAnswers.get(i);
                if (quiz.getCorrectAnswer().equalsIgnoreCase(userAnswer)) {
                    correct++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.historyQuizSummary.setText(total + " Questions · Score: " + correct + " / " + total);

        // 点击跳转到 HistoryDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistoryDetailActivity.class);
            intent.putExtra("record", history);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyDate, historyQuizSummary;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyDate = itemView.findViewById(R.id.historyDate);
            historyQuizSummary = itemView.findViewById(R.id.historyQuizSummary);
        }
    }
}

