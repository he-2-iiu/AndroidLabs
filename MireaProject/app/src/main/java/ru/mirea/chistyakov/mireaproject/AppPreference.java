package ru.mirea.chistyakov.mireaproject;


import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static ru.mirea.chistyakov.mireaproject.AppPreference.Preferences.*;

public abstract class AppPreference {
    public static SharedPreferences preferences;

    public enum Preferences {
        BROWSER_SEARCH,
        MUSIC_CHOICE
    }

    private final static Map<Preferences, String> _default_preferences = new HashMap<Preferences, String>() {
        {
            put(BROWSER_SEARCH, "https://www.ya.ru");
            put(MUSIC_CHOICE, "0");
        }
    };
    private final static Map<Preferences, String> _preferences_keys = new HashMap<Preferences, String>() {
        {
            put(BROWSER_SEARCH, "BROWSER_SEARCH");
            put(MUSIC_CHOICE, "MUSIC_CHOICE");
        }
    };

    public static void setPreferenceString(Preferences key, String value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_preferences_keys.get(key), value);
        editor.apply();
    }

    public static String getDefaultPreference(Preferences key) {
        return _default_preferences.get(key);
    }

    public static String getPreference(Preferences key) {
        String str_key = _preferences_keys.get(key);
        String text = preferences.getString(str_key, null);
        if (text == null) {
            text = getDefaultPreference(key);
        }
        return text;
    }
}