package com.example.mycomicapplication.spiderikanmanhua;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetSearchResult {
    public static ArrayList<Comic> Spridercomics(String searchtext){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(searchtext);
        Elements elements=document
                .select("ul.mh-list")
                .select("li");
        for (Element element:elements){
            String name=element
                    .select("div.mh-item")
                    .select("a")
                    .attr("title");
            String comicurl=element
                    .select("div.mh-item")
                    .select("a")
                    .attr("href");
            String coverurl=element
                    .select("div.mh-item")
                    .select("p")
                    .attr("style");
            coverurl=coverurl.substring(22);
            coverurl = coverurl.substring(0,coverurl.length() - 1);
            Comic comic=new Comic(name,comicurl,coverurl);
            comics.add(comic);
        }
        return comics;
    }
}
