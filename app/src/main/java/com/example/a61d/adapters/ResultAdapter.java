package com.example.a61d.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a61d.R;
import com.example.a61d.models.Quiz;

import java.util.HashMap;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private final List<Quiz> quizList;
    private final HashMap<Integer, String> userAnswers;

    public ResultAdapter(List<Quiz> quizList, HashMap<Integer, String> userAnswers) {
        this.quizList = quizList;
        this.userAnswers = userAnswers;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        String correct = quiz.getCorrectAnswer();
        String userAns = userAnswers.get(position);
        List<String> options = quiz.getOptions();

        holder.question.setText("Q" + (position + 1) + ": " + quiz.getQuestion());

        holder.optionA.setText("A. " + options.get(0));
        holder.optionB.setText("B. " + options.get(1));
        holder.optionC.setText("C. " + options.get(2));
        holder.optionD.setText("D. " + options.get(3));

        holder.optionsGroup.setEnabled(false); // 禁用点击

        RadioButton[] radios = {holder.optionA, holder.optionB, holder.optionC, holder.optionD};
        String[] labels = {"A", "B", "C", "D"};

        for (int i = 0; i < 4; i++) {
            RadioButton btn = radios[i];
            if (labels[i].equals(correct)) {
                btn.setTextColor(Color.parseColor("#388E3C")); // 正确答案绿色
            }
            if (labels[i].equals(userAns)) {
                btn.setChecked(true);
                if (!userAns.equals(correct)) {
                    btn.setTextColor(Color.RED); // 错误答案红色
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        RadioGroup optionsGroup;
        RadioButton optionA, optionB, optionC, optionD;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionText);
            optionsGroup = itemView.findViewById(R.id.optionsGroup);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
        }
    }
}
