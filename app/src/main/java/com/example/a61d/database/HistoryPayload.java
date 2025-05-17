package com.example.a61d.database;

import com.example.a61d.models.Quiz;
import java.util.HashMap;
import java.util.List;

public class HistoryPayload {
    public List<Quiz> quizList;
    public HashMap<Integer, String> userAnswers;

    public HistoryPayload(List<Quiz> quizList, HashMap<Integer, String> userAnswers) {
        this.quizList = quizList;
        this.userAnswers = userAnswers;
    }
}

