package com.example.anitac.parsetigram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anitac.parsetigram.Models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String imagepath = "/storage/emulated/0/DCIM/Camera/IMG_20180710_160023.jpg"; //enter hardcode image here, not nessesary
    private EditText mDescription;
    private Button mCreate;
    private Button refreshBtn;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDescription = findViewById(R.id.ahDescription);
        mCreate = findViewById(R.id.ahCreate);
        refreshBtn = findViewById(R.id.ahRefresh);

        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });

        mCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String description= mDescription.getText().toString();
                final ParseUser currentUser = ParseUser.getCurrentUser();

                final File imageFile = new File(imagepath); //TODO here we take pictures or search for picture

                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }*/

                final ParseFile parseFile = new ParseFile(imageFile);
                createPost(description, parseFile, currentUser);
            }
        });
    }

    //function to create posts
    public void createPost(String description, ParseFile imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("HomeActivity", "Create post success");
                }
                else{
                    e.printStackTrace();
                    Log.e("HomeActivity", "create post failed");
                }
            }
        });
    }

    //shows the 20 most recent posts
    public void loadTopPosts(){
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop()
                .withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e== null){
                    for(int i = 0; i < objects.size(); i++){
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //designed to start activity to take pictures
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            //
        }
    }
}
