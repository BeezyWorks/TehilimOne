package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.content.Context;

import com.mattaniahbeezy.wisechildtehilim.R;

/**
 * Created by Mattaniah on 2/16/2015.
 */
public class NameUtility {
    String[] letters;
    public NameUtility(Context context){
        letters=context.getResources().getStringArray(R.array.alphabet);
    }

    public String getName(String name) {
        name=name.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            String letter=getLetter(c);
            if (letter!=null) {
                builder.append(c+"\n");
                builder.append(getLetter(c));
                builder.append("\n\n");
            }
        }
        return builder.toString();
    }

    private String getLetter(char c) {
        if (c == 'א')
            return letters[0];
        if (c == 'ב')
            return letters[1];
        if (c == 'ג')
            return letters[2];
        if (c == 'ד')
            return letters[3];
        if (c == 'ה')
            return letters[4];
        if (c == 'ו')
            return letters[5];
        if (c == 'ז')
            return letters[6];
        if (c == 'ח')
            return letters[7];
        if (c == 'ט')
            return letters[8];
        if (c == 'י')
            return letters[9];
        if (c == 'כ'||c=='ך')
            return letters[10];
        if (c == 'ל')
            return letters[11];
        if (c == 'מ'||c=='ם')
            return letters[12];
        if (c == 'נ'||c=='ן')
            return letters[13];
        if (c == 'ס')
            return letters[14];
        if (c == 'ע')
            return letters[15];
        if (c == 'פ'||c=='ף')
            return letters[16];
        if (c == 'צ'||c=='ץ')
            return letters[17];
        if (c == 'ק')
            return letters[18];
        if (c == 'ר')
            return letters[19];
        if (c == 'ש')
            return letters[20];
        if (c == 'ת')
            return letters[21];

        return null;
    }
}
