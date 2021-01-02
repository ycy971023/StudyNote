package com.example.mycomicapplication.javabean;

import java.io.Serializable;

public class Comic implements Serializable {
    private String name;
    private String comicurl;
    private String coverurl;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComicurl() {
        return comicurl;
    }

    public Comic(String name, String comicurl, String coverurl) {
        this.name = name;
        this.comicurl = comicurl;
        this.coverurl = coverurl;
    }

    public void setComicurl(String comicurl) {
        this.comicurl = comicurl;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
