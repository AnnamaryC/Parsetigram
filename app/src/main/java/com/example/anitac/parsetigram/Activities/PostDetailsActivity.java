package com.example.anitac.parsetigram.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.R;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {
    Context context;
    TextView username;
    TextView description;
    ImageView photo;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        username = findViewById(R.id.pdaUsername);
        description = findViewById(R.id.pdaDescription);
        photo = findViewById(R.id.pdaImage);

        post = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        username.setText(post.getUser().getUsername());
        description.setText(post.getDescription());
        Glide.with(context).load(post.getImage().getUrl()).into(photo);

    }
}
