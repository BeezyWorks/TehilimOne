package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mattaniah on 7/16/2015.
 */
public class SettingsUtil {
    public static final String savedChapterKey = "key:savedChapter";
    SharedPreferences sharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SettingsUtil(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getSavedChapter() {
        return sharedPreferences.getInt(savedChapterKey, 0);
    }

    public void saveChapter(int chapter) {
        sharedPreferences.edit().putInt(savedChapterKey, chapter).apply();
    }

}
