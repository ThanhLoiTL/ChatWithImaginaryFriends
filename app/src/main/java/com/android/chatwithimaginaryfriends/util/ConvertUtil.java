package com.android.chatwithimaginaryfriends.util;

import android.os.Build;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertUtil {
    public static List<String> stringToArray(String triggerWords) {
        List<String> arrString = new ArrayList<>();
        if(triggerWords != null) {
            arrString  = new ArrayList<>(Arrays.asList(triggerWords.split(",")));
        }
        return arrString;
    }

    public static String arrayToString(List<String> list){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.join(",", list);
        }
        return null;
    }
}
