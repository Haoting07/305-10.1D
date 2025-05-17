package com.example.a61d.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz_history.db";

    // ✅ 将版本从 1 改为 2
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "history";

    public HistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ✅ 初次建表时包含 fullJson
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "date TEXT," +
                "score INTEGER," +
                "fullJson TEXT)";
        db.execSQL(create);
    }

    // ✅ 升级数据库结构时添加 fullJson 字段
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN fullJson TEXT");
        }
    }

    // ✅ 插入记录，包括完整答题 JSON
    public void insertHistory(String username, String dateTime, int score, String fullJson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("date", dateTime);
        values.put("score", score);
        values.put("fullJson", fullJson);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<QuizHistory> getAllHistory() {
        List<QuizHistory> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String date = cursor.getString(2);
                int score = cursor.getInt(3);
                String json = cursor.getString(4);
                list.add(new QuizHistory(id, username, date, score, json));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}


