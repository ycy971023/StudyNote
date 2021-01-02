package com.example.mycomicapplication.spidermanhuax;

import com.example.mycomicapplication.javabean.Chapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetChapter {
    public static ArrayList<Chapter> SpriderChapter(String cover){
        ArrayList<Chapter> chapters=new ArrayList<>();
        Document document= Jsoup.parse(cover);
        Elements elements=document
                .select(".detail-list-form-con")
                .select("a");
        for(Element element:elements){
            String name=element
                    .ownText();
            String chapterurl=element
                    .attr("href");
            Chapter chapter=new Chapter();
            chapter.setId(0);
            chapter.setChaptername(name);
            chapter.setChapterurl(chapterurl);
            chapters.add(chapter);
        }
    return chapters;
    }

}
