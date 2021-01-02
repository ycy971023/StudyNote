package com.example.mycomicapplication.spiderikanmanhua;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetTypeInfo {
    public static ArrayList<Comic> SpriderComic(String html){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements elements=document
                .select(".mh-list")
                .select("li");
        for (Element element:elements){
            String name=element
                    .select("a")
                    .attr("title");
            String url=element
                    .select("a")
                    .attr("href");
            String coverurl=element
                    .select(".mh-cover")
                    .attr("style");
            coverurl=coverurl.substring(22);
            coverurl = coverurl.substring(0,coverurl.length() - 1);
            Comic comic=new Comic(name,url,coverurl);
            comics.add(comic);
        }
        return comics;

    }
}
