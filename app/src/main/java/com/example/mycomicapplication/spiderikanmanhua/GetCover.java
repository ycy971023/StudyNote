package com.example.mycomicapplication.spiderikanmanhua;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetCover {
    public static ArrayList<Comic> SpriderComic(String html) {
        ArrayList<Comic> comics = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document
                .select("div.swiper-wrapper")
                .select("ul.index-original-list")
                .select("li");
        for(Element element:elements){
            String name=element
                    .select(".cover")
                    .select("a")
                    .attr("title");
            String cover=element
                    .select(".cover")
                    .select("a")
                    .select("img")
                    .attr("src");
            String url=element
                    .select(".cover")
                    .select("a")
                    .attr("href");
            Comic comic=new Comic(name,url,cover);
            comics.add(comic);
        }
        ArrayList<Comic> new_comics = new ArrayList<>();
        new_comics.addAll(comics.subList(0,10));
        return new_comics;

    }
}
