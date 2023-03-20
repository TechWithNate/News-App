package com.tech.apexnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlogPost extends AppCompatActivity {

    private TextView title;
    private TextView news_source;
    private TextView time_stamp;
    private TextView blog_post;
    private ImageView thumbnail;

    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;

    private Model blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
        initViews();


        blog = (Model) getIntent().getSerializableExtra("blog");
        if (null != blog) {
            title.setText(blog.getTitle());
            news_source.setText(blog.getNewsSource());
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(Long.parseLong(blog.getTimeStamp())).getTime());
            time_stamp.setText(formattedDate);
            blog_post.setText(blog.getNews());
            Glide.with(BlogPost.this)
                    .load(blog.getThumbnailURL())
                    .dontAnimate()
                    .into(thumbnail);
        }


    }

    private void initViews() {
        title = findViewById(R.id.title);
        news_source = findViewById(R.id.news_source);
        time_stamp = findViewById(R.id.time_posted);
        blog_post = findViewById(R.id.news_article);
        thumbnail = findViewById(R.id.thumbnail);
    }
}