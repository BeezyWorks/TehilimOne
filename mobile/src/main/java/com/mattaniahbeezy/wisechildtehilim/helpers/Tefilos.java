package com.mattaniahbeezy.wisechildtehilim.helpers;

import com.mattaniahbeezy.wisechildtehilim.R;

/**
 * Created by Mattaniah on 10/25/2015.
 */
public enum Tefilos {
    Parnasah, Yeshua, Sick, Children, Judgement, Childbirth, Shidduch, DeathBed, Yahrzeit,;

    public String getDescription() {
        switch (this) {
            case Parnasah:
                return "פרנסה";
            case Yeshua:
                return "ישועה";
            case Sick:
                return "רפואה";
            case Yahrzeit:
                return "יארצייט";
            case Children:
                return "בנים";
            case Judgement:
                return "לדין טוב";
            case Childbirth:
                return "לידה קל";
            case Shidduch:
                return "לשידוכים";
            case DeathBed:
                return "לגוסס";
        }
        return "I am error";
    }

    public int getDrawableId() {
        switch (this) {
            case Yahrzeit:
                return R.drawable.yahrtzeit;
        }
        return R.drawable.ic_settings;
    }
}
