package com.example.mycomicapplication.spidermanhuadb;

import android.util.Log;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Getcover {
    public static ArrayList<Comic> SpriderComic(String html) {
        ArrayList<Comic> comics = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document
                .select("ul.list-group")
                .select("li");
        for(Element element:elements){
            String name=element
                    .select(".media")
                    .select("a")
                    .attr("title");
            String cover=element
                    .select("img")
                    .attr("src");
            String url=element
                    .select(".media")
                    .select("a")
                    .attr("href");
            Comic comic=new Comic(name,url,cover);
            comics.add(comic);
        }
        return comics;

    }
}