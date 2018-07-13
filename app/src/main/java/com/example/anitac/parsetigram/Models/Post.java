package com.example.anitac.parsetigram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by anitac on 7/10/18.
 */

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt"; //function created at already exists
    public static final String KEY_HANDLE= "handle";
    public static final String KEY_PROFILE_PICTURE = "profilePicture";
    public static final String KEY_PROFILE_INFORMATION = "bioInformation";

    public Post(){

    }

    public Post(String description, ParseUser user, ParseFile image){
        put(KEY_DESCRIPTION, description);
        put(KEY_USER, user);
        put(KEY_IMAGE, image);
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);

    }

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_PICTURE);
    }

    public ParseFile getHandle(){
        return getParseFile(KEY_HANDLE);
    }

    public static class Query extends ParseQuery<Post> {
        public Query(){
            super(Post.class);
        }

        public Query getTop(){
            setLimit(40);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }
    }
}
