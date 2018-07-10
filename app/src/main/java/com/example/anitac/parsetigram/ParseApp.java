package com.example.anitac.parsetigram;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by anitac on 7/9/18.
 */

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final  Parse.Configuration config = new Parse.Configuration.Builder(this)

                .build();

        Parse.initialize(config);
    }
}
