package com.example.mycomicapplication.spidermanhuadb;

import com.example.mycomicapplication.javabean.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetSearchResult {
    public static ArrayList<Comic> Spridercomics(String searchtext) {
        ArrayList<Comic> comics = new ArrayList<>();
        Document document = Jsoup.parse(searchtext);
        Elements elements = document
                .select(".row")
                .select("div.col-sm-2");
        for (Element element : elements) {
            String name = element
                    .select(".comicbook-index")
                    .select("a")
                    .attr("title");
            String comicurl = element
                    .select(".comicbook-index")
                    .select("a")
                    .attr("href");
            String coverurl = element
                    .select(".img-fluid")
                    .select("img")
                    .attr("src");
            Comic comic = new Comic(name, comicurl, coverurl);
            comics.add(comic);
        }
        return comics;
    }
}
