package com.mattaniahbeezy.wisechildtehilim.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.fragments.MainFragment;
import com.mattaniahbeezy.wisechildtehilim.fragments.NavigationDrawerFragment;
import com.mattaniahbeezy.wisechildtehilim.fragments.TehilimFragment;
import com.mattaniahbeezy.wisechildtehilim.helpers.HostActivity;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements HostActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;
    MainFragment mainFragment;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Crashlytics.getInstance().core.setUserIdentifier(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainFragment = new TehilimFragment();
        navigationDrawerFragment = new NavigationDrawerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, navigationDrawerFragment).commit();
    }


    public void setDrawerToggle(Toolbar toolbar) {
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.abc_action_bar_home_description, R.string.abc_action_bar_up_description) {
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void resetColors() {
        if (mainFragment != null)
            mainFragment.updateColor();
        navigationDrawerFragment.resetColor();
    }

    @Override
    public void goToChapter(int chapter) {
        mainFragment.goToChapter(chapter);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void informNewChapter(int chapter) {
        navigationDrawerFragment.setSelectedChapter(chapter);
    }
}
