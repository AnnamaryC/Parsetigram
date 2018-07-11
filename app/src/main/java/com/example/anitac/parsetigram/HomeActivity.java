package com.example.anitac.parsetigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anitac.parsetigram.Models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 23;
    private SwipeRefreshLayout swipeContainer; //for refresh
    RecyclerView rvPosts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        rvPosts = (RecyclerView) findViewById(R.id.RecyclerTimeline);


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadTopPosts();
                swipeContainer.setRefreshing(false);
            }
        });

        loadTopPosts();

    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent intent = new Intent(this, ComposeActivity.class);

                startActivityForResult(intent,REQUEST_CODE); //wrapping
                //shows tweet after composing on timeline, UNLIKE startActivity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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



}
