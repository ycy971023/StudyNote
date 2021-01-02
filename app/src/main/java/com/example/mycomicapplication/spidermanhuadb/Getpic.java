package com.example.mycomicapplication.spidermanhuadb;

import android.util.Log;

import com.example.mycomicapplication.javabean.Pic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Getpic {
    public static ArrayList<Pic> spriderPic(String html){
        ArrayList<Pic> pics=new ArrayList<>();
        Pic pic = new Pic();
        Document document = Jsoup.parse(html);
        Elements elements=document
                .select(".text-center")
                .select("img.img-fluid");
        for(Element element:elements){
            String url=element.attr("src");
            pic.setUrl(url);
        }
        pics.add(pic);
        return pics;
    }
}
