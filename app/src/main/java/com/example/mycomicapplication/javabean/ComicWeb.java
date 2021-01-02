package com.example.mycomicapplication.javabean;

public class ComicWeb {
    String webname;
    String search;

    public ComicWeb(String webname, int webcover, String weburl,int id,String search) {
        this.webname = webname;
        this.webcover = webcover;
        this.weburl = weburl;
        this.id=id;
        this.search=search;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int webcover;
    private int id;

    public int getWebcover() {
        return webcover;
    }

    public void setWebcover(int webcover) {
        this.webcover = webcover;
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    String weburl;
}
