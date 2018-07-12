package com.example.anitac.parsetigram.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anitac.parsetigram.Activities.PostDetailsActivity;
import com.example.anitac.parsetigram.Models.Post;
import com.example.anitac.parsetigram.R;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by anitac on 7/11/18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;
    //pass in the tweet aray in the constructor
    public PostAdapter(List<Post> posts){
        this.mPosts = posts;
    }
    Context context;


    //create viewholder class clickable for details
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView instaImage;
        public TextView username;
        public  TextView actualPost;
        public TextView actualDate;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findviewby id
            instaImage = itemView.findViewById(R.id.postImage);
            username = itemView.findViewById(R.id.userHandle);
            actualPost = itemView.findViewById(R.id.actualDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Log.d("Post Adapter", "Showing details of specific post.");
                // get the post at the position, this won't work if the class is static
                Post post = mPosts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the post using parceler, use its short name as a key
                intent.putExtra("user", Parcels.wrap(post));
                // show the activity
                context.startActivity(intent);
            }
        }
    }

    //for each row, inflate the layout and cache references into viewholder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post,parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //which post option we're going to show
        //get data according to position
        Post post = mPosts.get(position);

        //populate the views according to this data
        try {
            holder.username.setText(post.getUser().fetchIfNeeded().getUsername());
            holder.actualPost.setText(post.getDescription());
            //holder.actualDate.setText(post.createdAt);
            Glide.with(context).load(post.getImage().getUrl()).into(holder.instaImage);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //to see the tweets
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

}
