package com.example.mycomicapplication.spidermanhuadb;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetmanhuadbTypeinfo {
    public static ArrayList<Comic> SpriderComic(String html){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements elements=document
                .select(".comic-main-section")
                .select(".media");
        for (Element element:elements){
            String name=element
                    .select(".media-body")
                    .select("h2")
                    .select("a")
                    .text();
            String url=element
                    .select("a")
                    .attr("href");
            String coverurl=element
                    .select("img")
                    .attr("src");
            Comic comic=new Comic(name,url,coverurl);
            comics.add(comic);
        }
        return comics;

    }
}
