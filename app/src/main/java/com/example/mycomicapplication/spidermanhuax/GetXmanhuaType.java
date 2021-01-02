package com.example.mycomicapplication.spidermanhuax;

import com.example.mycomicapplication.javabean.Type;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetXmanhuaType {
    public static ArrayList<Type> SpriderType(String html){
        ArrayList<Type> types=new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements=document
                .select(".list")
                .select("li");
        for(Element element:elements){
            String name=element
                    .select("a")
                    .attr("title");
            String url=element
                    .select("a")
                    .attr("href");
            Type type=new Type();
            type.setTypename(name);
            type.setTypeurl(url);
            types.add(type);
        }
        return types;

    }
}
