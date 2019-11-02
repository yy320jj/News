package com.example.ccbb.myapplication.entity;

import java.util.List;

public class News {
    private String title;
    private String date;
    private String imgUrl;
    private String url;

    public News(String title, String date, String imgUrl, String url) {
        this.title = title;
        this.date = date;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
