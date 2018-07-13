package com.example.anitac.parsetigram.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

public class ComposeActivity extends AppCompatActivity {
    private ImageView mImage;
    private EditText mDescription;
    private Button mPost;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int PICK_PHOTO_CODE = 1046;
    File photoFile;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        mPost = findViewById(R.id.composeButton);
        mImage = findViewById(R.id.acPhoto);
        mDescription = findViewById(R.id.acDescription);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //intent to create camera
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(ComposeActivity.this, "com.anitac.fileprovider", photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) { //does it have a camera
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        //create a post
        mPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String description= mDescription.getText().toString();
                final ParseUser currentUser = ParseUser.getCurrentUser();
                final ParseFile parseFile = new ParseFile(photoFile);

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
                    Log.d("ComposeActivity", "Create post success");
                    Toast.makeText(ComposeActivity.this, "Photo successfully posted!", Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(ComposeActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    e.printStackTrace();
                    Log.e("ComposeActivity", "create post failed");
                }
            }
        });
    }


    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d("ComposeActivity", "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //designed to start activity to take pictures
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                mImage = (ImageView) findViewById(R.id.acPhoto);
                mImage.setImageBitmap(takenImage);

                //Intent intent = new Intent();
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

        else if(requestCode == PICK_PHOTO_CODE){
            if (data != null) {
                Uri photoUri = data.getData();
                // Do something with the photo based on Uri
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    // Load the selected image into a preview
                    mImage = (ImageView) findViewById(R.id.acPhoto);
                    mImage.setImageBitmap(selectedImage);
                    //photoFile = photoFile.getAbsoluteFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error, can't choose photo from gallery", Toast.LENGTH_LONG);

                }

            }
        }
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

}
