package com.example.anitac.parsetigram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anitac.parsetigram.Models.Post;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by anitac on 7/11/18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;
    //pass in the tweet aray in the constructor
    public PostAdapter(List<Post> posts){
        mPosts = posts;
    }
    Context context;


    //create viewholder class
    public static class ViewHolder extends RecyclerView.ViewHolder{
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
