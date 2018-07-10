package com.example.anitac.parsetigram;

import android.app.Application;
import com.example.anitac.parsetigram.Models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by anitac on 7/9/18.
 */

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final  Parse.Configuration config = new Parse.Configuration.Builder(this)
                .applicationId("insta-id")
                .clientKey("insta-pass")
                .server("http://anitac-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(config);
    }
}
