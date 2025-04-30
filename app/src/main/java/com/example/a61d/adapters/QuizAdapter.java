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

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private final List<Quiz> quizList;
    private final HashMap<Integer, String> userAnswers = new HashMap<>();
    private boolean showAnswers = false;

    public QuizAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public HashMap<Integer, String> getUserAnswers() {
        return userAnswers;
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < quizList.size(); i++) {
            String correct = quizList.get(i).getCorrectAnswer();
            String selected = userAnswers.get(i);
            if (correct != null && correct.equalsIgnoreCase(selected)) {
                score++;
            }
        }
        return score;
    }

    public void setShowAnswers(boolean show) {
        showAnswers = show;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.question.setText("Q" + (position + 1) + ". " + quiz.getQuestion());

        List<String> opts = quiz.getOptions();
        holder.optionA.setText("A. " + opts.get(0));
        holder.optionB.setText("B. " + opts.get(1));
        holder.optionC.setText("C. " + opts.get(2));
        holder.optionD.setText("D. " + opts.get(3));

        holder.optionsGroup.setOnCheckedChangeListener(null);
        holder.optionsGroup.clearCheck();

        if (userAnswers.containsKey(position)) {
            switch (userAnswers.get(position)) {
                case "A": holder.optionA.setChecked(true); break;
                case "B": holder.optionB.setChecked(true); break;
                case "C": holder.optionC.setChecked(true); break;
                case "D": holder.optionD.setChecked(true); break;
            }
        }

        holder.optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == holder.optionA.getId()) {
                userAnswers.put(position, "A");
            } else if (checkedId == holder.optionB.getId()) {
                userAnswers.put(position, "B");
            } else if (checkedId == holder.optionC.getId()) {
                userAnswers.put(position, "C");
            } else if (checkedId == holder.optionD.getId()) {
                userAnswers.put(position, "D");
            }
        });


        if (showAnswers) {
            String correct = quiz.getCorrectAnswer();
            highlightAnswer(holder, correct);
        } else {
            resetColor(holder);
        }
    }

    private void highlightAnswer(QuizViewHolder holder, String correct) {
        resetColor(holder);
        switch (correct.toUpperCase()) {
            case "A": holder.optionA.setTextColor(Color.GREEN); break;
            case "B": holder.optionB.setTextColor(Color.GREEN); break;
            case "C": holder.optionC.setTextColor(Color.GREEN); break;
            case "D": holder.optionD.setTextColor(Color.GREEN); break;
        }
    }

    private void resetColor(QuizViewHolder holder) {
        holder.optionA.setTextColor(Color.BLACK);
        holder.optionB.setTextColor(Color.BLACK);
        holder.optionC.setTextColor(Color.BLACK);
        holder.optionD.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return quizList != null ? quizList.size() : 0;
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        RadioGroup optionsGroup;
        RadioButton optionA, optionB, optionC, optionD;

        public QuizViewHolder(@NonNull View itemView) {
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

