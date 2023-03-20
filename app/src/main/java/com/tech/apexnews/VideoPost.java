package com.tech.apexnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideoPost extends AppCompatActivity {

    private EditText title;
    private EditText videoUrl;
    private MaterialButton selectVideo;
    private MaterialButton publish;
    private ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference videoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_post);
        initViews();

        database = FirebaseDatabase.getInstance();
        videoRef = database.getReference("videos").push();
        
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishVideo();
            }
        });
        
    }

    private void publishVideo() {
        progressDialog.setTitle("Uploading Video");
        progressDialog.setMessage("Video Uploading to Database");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (title.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
            title.requestFocus();
        } else if (videoUrl.getText().toString().isEmpty()) {
            Toast.makeText(this, "Select a video", Toast.LENGTH_SHORT).show();
            videoUrl.requestFocus();
        }else{
            VideoModel model = new VideoModel(title.getText().toString(), videoUrl.getText().toString());
            videoRef.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(VideoPost.this, "Published", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VideoPost.this, VideoFragment.class));
                        finish();
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }

    private void initViews(){
        title = findViewById(R.id.video_title);
        videoUrl = findViewById(R.id.video_url);
        selectVideo = findViewById(R.id.select_video);
        publish = findViewById(R.id.publish);
        progressDialog = new ProgressDialog(this);
    }
    
}