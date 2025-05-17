package com.example.a61d.database;

import java.io.Serializable;

public class QuizHistory implements Serializable {
    private int id;
    private String username;
    private String dateTime;
    private int score;
    private String fullJson;

    public QuizHistory(int id, String username, String dateTime, int score, String fullJson) {
        this.id = id;
        this.username = username;
        this.dateTime = dateTime;
        this.score = score;
        this.fullJson = fullJson;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDateTime() { return dateTime; } // 原方法保留
    public int getScore() { return score; }
    public String getFullJson() { return fullJson; }

    // ✅ 新增用于适配显示历史时间
    public String getDate() {
        return dateTime;
    }
}
