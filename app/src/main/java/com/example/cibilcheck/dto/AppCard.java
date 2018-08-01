package com.example.cibilcheck.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class AppCard implements Serializable {
    @Expose
    private String title;
    @Expose
    private String thumbnail;
    @Expose
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AppCard(String url, String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
    }
}
