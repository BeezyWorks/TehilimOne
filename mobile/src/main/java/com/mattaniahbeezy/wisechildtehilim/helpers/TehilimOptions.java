package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mattaniah on 10/21/2015.
 */
public enum TehilimOptions {
    BY_SEFER, BY_WEEK, BY_MONTH;

    final static String key="key:tehilimOptionPref";

    public String getEnglishName() {
        switch (this) {

            case BY_SEFER:
                return "By Book";
            case BY_WEEK:
                return "Day of Week";
            case BY_MONTH:
                return "Day of Month";
        }
        return "I am error";
    }

    public String getHebrewName() {
        switch (this) {

            case BY_SEFER:
                return "לפי ספר";
            case BY_WEEK:
                return "לפי שבועה";
            case BY_MONTH:
                return "לפי חודש";
        }
        return "I am error";
    }

    public void setAsDefault(Context context){
        new SettingsUtil(context).getSharedPreferences().edit().putString(key, this.name()).apply();
    }

    public static TehilimOptions getSaved(Context context){
        SharedPreferences sharedPreferences = new SettingsUtil(context).getSharedPreferences();
        String defaultPref = sharedPreferences.getString(key, BY_SEFER.name());
        for (TehilimOptions options : TehilimOptions.values())
            if (defaultPref.equals(options.name()))
                return options;
        return BY_SEFER;
    }
}
