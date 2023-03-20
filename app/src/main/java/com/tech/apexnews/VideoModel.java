package com.tech.apexnews;

import java.io.Serializable;

public class VideoModel implements Serializable {

    private String title;
    private String videoUrl;

    public VideoModel() {
    }

    public VideoModel(String title, String videoUrl) {
        this.title = title;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
