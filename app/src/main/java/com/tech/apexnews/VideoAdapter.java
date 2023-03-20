package com.tech.apexnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private Context context;
    private ArrayList<VideoModel> videos;

    public VideoAdapter(Context context, ArrayList<VideoModel> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoModel model = videos.get(position);
        holder.title.setText(model.getTitle());
        holder.videoView.setVideoPath(model.getVideoUrl());

        MediaController mediaController = new MediaController(context);
//        CustomMediaController mediaController = new CustomMediaController(context, holder.videoView);
        //mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);
//        ViewGroup.LayoutParams params = mediaController.getLayoutParams();
//        params.width = 300; // Set the width to 300 pixels
//        params.height = 100; // Set the height to 100 pixels
//        mediaController.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private VideoView videoView;
        private TextView title;
        public ViewHolder(@NonNull View view) {
            super(view);

            videoView = view.findViewById(R.id.videoView);
            title = view.findViewById(R.id.title);
        }
    }
}
