package com.example.mycomicapplication.javabean;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String chapterurl;
    private int id;
    private String comicname;

    public String getComicname() {
        return comicname;
    }

    public void setComicname(String comicname) {
        this.comicname = comicname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterurl() {
        return chapterurl;
    }

    public void setChapterurl(String chapterurl) {
        this.chapterurl = chapterurl;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    private String chaptername;
}
