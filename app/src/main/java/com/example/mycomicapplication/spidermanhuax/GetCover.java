package com.example.mycomicapplication.spidermanhuax;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetCover {
    public static ArrayList<Comic> SpriderComic(String html){
        ArrayList<Comic> comics=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements elements=document
                .select(".index-list-con-info-inner")
                .select("a");
        Elements elements1=document
                .select(".carousel-right-list")
                .select(".carousel-right-item");
        for (Element element1:elements1){
            String name=element1
                    .select("a")
                    .attr("title");
            String comicurl=element1
                    .select("a")
                    .attr("href");
            String coverurl=element1
                    .select("a")
                    .select("img")
                    .attr("src");
            Comic comic=new Comic(name,comicurl,coverurl);
            comics.add(comic);
        }
        for(Element element:elements){
            String name=element
                    .attr("title");
            String comicurl=element
                    .attr("href");
            String coverurl=element
                    .select("img")
                    .attr("src");
            Comic comic=new Comic(name,comicurl,coverurl);
            comics.add(comic);
        }

        return comics;
    }

}
