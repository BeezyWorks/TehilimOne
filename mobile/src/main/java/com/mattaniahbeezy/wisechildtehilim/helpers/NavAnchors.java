package com.mattaniahbeezy.wisechildtehilim.helpers;

import com.mattaniahbeezy.wisechildtehilim.R;

/**
 * Created by Mattaniah on 10/25/2015.
 */
public enum NavAnchors {
    Yahrzeit, Share, Tefilos;

    public String getDescription() {
        switch (this) {

            case Yahrzeit:
                return "Yahrzeits";
            case Share:
                return "Share תהילים";
            case Tefilos:
                return "תפילות";

        }
        return "I am error";
    }

    public int getDrawableId() {
        switch (this) {
            case Yahrzeit:
                return R.drawable.yahrtzeit;
            case Share:
                return R.drawable.ic_google_circles_extended;
            case Tefilos:
                return R.drawable.ic_dots_horizontal;
        }
        return R.drawable.ic_settings;
    }
}
