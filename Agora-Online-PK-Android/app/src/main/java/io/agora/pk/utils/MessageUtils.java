package io.agora.pk.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

public class MessageUtils {

    private static ConcurrentHashMap<String, Object> maps = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> maps2 = new ConcurrentHashMap<>();

    public static String switchToChatJsonMsg(String msg) {
        maps.clear();
        maps.put("type", "chat");
        maps.put("data", msg);
        return new JSONObject(maps).toString();
    }

    public static String switchToCtrlMsg(boolean msg) {
        maps2.clear();
        maps2.put("type", "pkStatus");
        maps2.put("data", msg);
        return new JSONObject(maps2).toString();
    }

    public static Object getMessage(String content) {
        try {
            JSONObject obj = new JSONObject(content);
            String s = obj.optString("type", "");
            if (TextUtils.isEmpty(s))
                return null;

            if ("chat".equals(s)) {
                return obj.optString("data", "");
            } else if ("pkStatus".equals(s)) {
                return Boolean.parseBoolean(obj.optString("data"));
            }

        } catch (JSONException e) {
            throw new RuntimeException("json error!");
        }

        return null;
    }
}

