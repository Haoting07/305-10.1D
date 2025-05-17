package com.example.a61d.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferenceManager {

    private static final String PREF_NAME = "learning_prefs";
    private static final String KEY_USERS = "user_map";
    private static final String KEY_CURRENT_USER = "current_user";
    private static final String KEY_INTERESTS_PREFIX = "interests_";
    private static final String KEY_ACCOUNT_LEVEL_PREFIX = "account_level_"; // ✅ 新增字段

    private static final Gson gson = new Gson();

    // 设置当前登录用户
    public static void setCurrentUser(Context context, String username) {
        getPrefs(context).edit().putString(KEY_CURRENT_USER, username).apply();
    }

    public static String getCurrentUser(Context context) {
        return getPrefs(context).getString(KEY_CURRENT_USER, null);
    }

    // 注册用户
    public static void registerUser(Context context, String username, String password) {
        Map<String, String> users = getUserMap(context);
        users.put(username, password);
        saveUserMap(context, users);
    }

    // 验证用户登录
    public static boolean isValidLogin(Context context, String username, String password) {
        Map<String, String> users = getUserMap(context);
        return users.containsKey(username) && users.get(username).equals(password);
    }

    // 存储当前用户兴趣（多选）
    public static void saveUserInterests(Context context, List<String> interests) {
        String username = getCurrentUser(context);
        if (username != null) {
            getPrefs(context).edit()
                    .putString(KEY_INTERESTS_PREFIX + username, gson.toJson(interests))
                    .apply();
        }
    }

    // 获取当前用户兴趣列表
    public static List<String> getUserInterests(Context context) {
        String username = getCurrentUser(context);
        if (username == null) return new ArrayList<>();
        String json = getPrefs(context).getString(KEY_INTERESTS_PREFIX + username, "[]");
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // 从当前用户兴趣列表中随机选一个 topic（用于 Quiz 页面）
    public static String getUserTopic(Context context) {
        List<String> interests = getUserInterests(context);
        if (interests.isEmpty()) return "math";

        int randomIndex = (int) (Math.random() * interests.size());
        return interests.get(randomIndex);
    }

    // ✅ 新增：保存当前用户的账户等级（如 Pro / Plus）
    public static void saveUserAccountLevel(Context context, String level) {
        String username = getCurrentUser(context);
        if (username != null) {
            getPrefs(context).edit()
                    .putString(KEY_ACCOUNT_LEVEL_PREFIX + username, level)
                    .apply();
        }
    }

    // ✅ 新增：获取当前用户账户等级（默认为 Free）
    public static String getUserAccountLevel(Context context) {
        String username = getCurrentUser(context);
        if (username == null) return "Free";
        return getPrefs(context).getString(KEY_ACCOUNT_LEVEL_PREFIX + username, "Free");
    }

    // ✅ 清除登录用户（不清除注册数据）
    public static void clearSession(Context context) {
        getPrefs(context).edit().remove(KEY_CURRENT_USER).apply();
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static Map<String, String> getUserMap(Context context) {
        String json = getPrefs(context).getString(KEY_USERS, "{}");
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private static void saveUserMap(Context context, Map<String, String> userMap) {
        getPrefs(context).edit().putString(KEY_USERS, gson.toJson(userMap)).apply();
    }
}




