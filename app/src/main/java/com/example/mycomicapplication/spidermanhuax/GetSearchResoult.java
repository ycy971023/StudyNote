package com.example.mycomicapplication.spidermanhuax;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetSearchResoult {
    public static ArrayList<Comic> Spridercomics(String searchtext){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(searchtext);
        Elements elements=document
                .select(".mh-list")
                .select("li");
        for (Element element:elements){
            String name=element
                    .select(".title")
                    .select("a")
                    .attr("title");
            String comicurl=element
                    .select(".title")
                    .select("a")
                    .attr("href");
            String coverurl=element
                    .select(".mh-item")
                    .select("img")
                    .attr("src");
            Comic comic=new Comic(name,comicurl,coverurl);
            comics.add(comic);
        }
        return comics;
    }
}
