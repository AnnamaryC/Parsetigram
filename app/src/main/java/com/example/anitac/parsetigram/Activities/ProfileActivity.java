package com.example.anitac.parsetigram.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        bio = findViewById(R.id.paBio);
        profilePic = findViewById(R.id.paProfilePic);
        username = findViewById(R.id.paHandle);

        post = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        username.setText(post.getUser().getUsername());
        bio.setText(post.getBio());
        Glide.with(this).load(post.getProfileImage().getUrl()).into(profilePic);
    }
}
