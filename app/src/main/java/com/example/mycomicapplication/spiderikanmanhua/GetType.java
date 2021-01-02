package com.example.mycomicapplication.spiderikanmanhua;

import com.example.mycomicapplication.javabean.Type;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetType {
    public static ArrayList<Type> SpriderType(String html) {
        ArrayList<Type> types = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements1 = document
                .select(".full-class")
                .select(".container")
                .select("a");
        for (Element element : elements1) {
            String name = element
                    .text();
            String url = element
                    .attr("href");
            Type type = new Type();
            type.setTypename(name);
            type.setTypeurl(url);
            types.add(type);
        }
        Elements elements2=document
                .select(".box-header");
        for(Element element:elements2){
            String name=element
                    .select("h2")
                    .text();
            String url=element
                    .select(".more")
                    .attr("href");
            Type type = new Type();
            type.setTypename(name);
            type.setTypeurl(url);
            types.add(type);
        }
        return types;
    }
}
