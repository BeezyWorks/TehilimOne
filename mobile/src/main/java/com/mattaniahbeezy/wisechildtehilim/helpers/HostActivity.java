package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.support.v7.widget.Toolbar;

/**
 * Created by Mattaniah on 7/16/2015.
 */
public interface HostActivity {
    void setDrawerToggle(Toolbar toolbar);

    void resetColors();

    void goToChapter(int chapter);

    void closeDrawer();

    void informNewChapter(int chapter);
}
