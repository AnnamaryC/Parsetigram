package com.example.anitac.parsetigram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.Adapters.PostAdapter;
import com.example.anitac.parsetigram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 23;
    private SwipeRefreshLayout swipeContainer; //for refresh
    RecyclerView rvPosts;
    PostAdapter postAdapter;
    ArrayList<Post> posts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ParseObject.registerSubclass(Post.class);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        rvPosts = (RecyclerView) findViewById(R.id.RecyclerTimeline);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvPosts.setAdapter(postAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshingTopPosts();
                swipeContainer.setRefreshing(false);
            }
        });
        loadTopPosts();
    }

    private void refreshingTopPosts() {
        postAdapter.clear();
        postAdapter.addAll(posts);
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
                //shows post after composing on timeline, UNLIKE startActivity
                return true;
            case R.id.miLogout:
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseUser.logOut();
                currentUser = ParseUser.getCurrentUser(); // this will now be null
                Toast.makeText(this, "Logged out" , Toast.LENGTH_LONG);
                Intent logoutintent = new Intent(this, MainLoginActivity.class);
                startActivity(logoutintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //shows the 20 most recent posts
    public void loadTopPosts(){
        final ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop()
                .withUser();


        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e== null){
                    for(int i = 0; i <objects.size() ; i++){ //shows tops from most recent
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                        posts.add(0,objects.get(i));
                        postAdapter.notifyItemInserted(0);
                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }







}
