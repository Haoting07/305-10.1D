package com.example.a61d.models;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    public String question;
    public List<String> options;
    public String correct_answer;

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }
}



