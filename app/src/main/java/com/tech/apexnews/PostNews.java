package com.tech.apexnews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PostNews extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    private TextView title;
    private TextView news_source;
    private TextView blog_post;
    private ImageView thumbnail;
    private MaterialButton publish;
    private Uri imageUri;
    private ProgressDialog progressDialog;

    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    private Model blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);
        initViews();

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("Blog");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPost();
            }
        });

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });




    }

    private void publishPost() {

        progressDialog.setMessage("Posting News Article");
        progressDialog.setTitle("Post");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String postTitle = title.getText().toString();
        String postSource = news_source.getText().toString();
        String newsArticle = blog_post.getText().toString();
        if (postTitle.isEmpty()){
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
            title.requestFocus();
        } else if (postSource.isEmpty()) {
            Toast.makeText(this, "Enter Source", Toast.LENGTH_SHORT).show();
            news_source.requestFocus();
        }else if (newsArticle.isEmpty()){
            Toast.makeText(this, "Enter Blog Post", Toast.LENGTH_SHORT).show();
            blog_post.requestFocus();
        }else if (null == thumbnail){
            Toast.makeText(this, "Select Thumbnail Image", Toast.LENGTH_SHORT).show();
        }else {
            startPost(postTitle, postSource, newsArticle);
        }
    }

    private void startPost(String postTitle, String postSource, String newsArticle) {

        StorageReference imagePath = mStorageRef.child("Blog_images").child(imageUri.getLastPathSegment());
        imagePath.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

            imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    DatabaseReference post = mDatabaseRef.push();

                    Map<String, String> blogData = new HashMap<>();
                    blogData.put("title", postTitle);
                    blogData.put("newsSource", postSource);
                    blogData.put("thumbnailURL", uri.toString());
                    blogData.put("news", newsArticle);
                    blogData.put("timeStamp", String.valueOf(System.currentTimeMillis()));

                    post.setValue(blogData);
                    progressDialog.dismiss();
                    startActivity(new Intent(PostNews.this, HomeFragment.class));
                    finish();
                }
            });

            progressDialog.dismiss();
        });





    }

    private void initViews(){
        publish = findViewById(R.id.post_btn);
        title = findViewById(R.id.title);
        news_source = findViewById(R.id.news_source);
        blog_post = findViewById(R.id.blog);
        thumbnail = findViewById(R.id.thumbnail);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if (null != data){
                imageUri = data.getData();
                thumbnail.setImageURI(imageUri);
            }else {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }

        }
    }
}