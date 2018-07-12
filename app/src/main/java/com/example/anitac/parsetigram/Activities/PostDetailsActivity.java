package com.example.anitac.parsetigram.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.R;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 5;
    Context context = null ;
    TextView username;
    TextView description;
    TextView createdAt;
    ImageView photo;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        username = findViewById(R.id.pdaUsername);
        description = findViewById(R.id.pdaDescription);
        createdAt = findViewById(R.id.pdaCreatedAt);
        photo = findViewById(R.id.pdaImage);

        post = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        Date date = post.getCreatedAt(); //function already exits
        String actualDate = date.toString();
        actualDate = getRelativeTimeAgo(post.getCreatedAt().toString());

        username.setText(post.getUser().getUsername());
        description.setText(post.getDescription());
        createdAt.setText(actualDate);
        Glide.with(context).load(post.getImage().getUrl()).into(photo);

    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mdBack:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivityForResult(intent, REQUEST_CODE); //wrapping
                //shows post after composing on timeline, UNLIKE startActivity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }
}
