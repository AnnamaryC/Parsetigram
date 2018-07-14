package com.example.anitac.parsetigram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.R;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {
    TextView bio;
    ImageView profilePic;
    TextView username;
    Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        bio = findViewById(R.id.paBio);
        profilePic = findViewById(R.id.paProfilePic);
        username = findViewById(R.id.paHandle);

        post = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        username.setText(post.getUser().getUsername());
        bio.setText(post.getBio());
        Glide.with(this).load(post.getProfileImage().getUrl()).into(profilePic);
    }

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
                startActivityForResult(intent, 43); //wrapping
                //shows post after composing on timeline, UNLIKE startActivity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
