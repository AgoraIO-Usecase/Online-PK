package io.agora.pk.utils;

import android.text.TextUtils;

public class StringUtils {
    private final static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String random(int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append( chars.charAt((int) (Math.random() * (len + 1))));
        }

        return sb.toString();
    }

    public static boolean validate(String s) {
        return null != s && !TextUtils.isEmpty(s);
    }
}
