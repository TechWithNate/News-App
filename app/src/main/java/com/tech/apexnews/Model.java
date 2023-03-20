package com.tech.apexnews;

import java.io.Serializable;

public class Model implements Serializable {
    private int id;
    private String title;
    private String news;
    private String thumbnailURL;
    private String newsSource;
    private String timeStamp;

    public Model() {
    }

    public Model(int id, String title, String news, String thumbnailURL, String newsSource, String timeStamp) {
        this.id = id;
        this.title = title;
        this.news = news;
        this.thumbnailURL = thumbnailURL;
        this.newsSource = newsSource;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", news='" + news + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                ", newsSource='" + newsSource + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
