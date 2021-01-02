package com.example.mycomicapplication.spidermanhuax;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetXmanhuaTypeInfo {
    public static ArrayList<Comic> SpriderComic(String html){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements elements=document
                .select(".mh-list")
                .select("li");
        for (Element element:elements){
            String name=element
                    .select(".mh-item-detali")
                    .select("a")
                    .attr("title");
            String url=element
                    .select(".mh-item-detali")
                    .select("a")
                    .attr("href");
            String coverurl=element
                    .select(".mh-item")
                    .select(".mh-cover")
                    .attr("src");
            Comic comic=new Comic(name,url,coverurl);
            comics.add(comic);
        }
        return comics;

    }
}
