package com.mattaniahbeezy.wisechildtehilim;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Mattaniah on 10/21/2015.
 */
public class TehilimApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rEHhwS402m0UrpfvbGQtNjWxxYGtSq6StsL6Cfao", "AsglJoseogeZCP8BktTuRdVFB0LhtlZMfPHPcZag");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser==null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    String tag = "Parse anon sign-in";
                    if (e != null) {
                        Log.d(tag, "Anonymous login failed.");
                        e.printStackTrace();
                    } else {
                        Log.d(tag, "Anonymous user logged in.");
                    }
                }
            });
        }
    }
}
