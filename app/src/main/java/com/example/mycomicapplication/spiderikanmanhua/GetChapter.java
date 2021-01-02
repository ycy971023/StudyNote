package com.example.mycomicapplication.spiderikanmanhua;

import com.example.mycomicapplication.javabean.Chapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetChapter {
    public static ArrayList<Chapter> SpriderChapter(String cover) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        Document document = Jsoup.parse(cover);
        Elements elements = document
                .select("#detail-list-select")
                .select("li");
        for (Element element : elements) {
            String name = element
                    .select("a")
                    .text();
            String chapterurl = element
                    .select("a")
                    .attr("href");
            Chapter chapter = new Chapter();
            chapter.setId(2);
            chapter.setChaptername(name);
            chapter.setChapterurl(chapterurl);
            chapters.add(chapter);
        }
        return chapters;
    }
}
