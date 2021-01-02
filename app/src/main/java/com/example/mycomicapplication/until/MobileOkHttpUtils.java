package com.example.mycomicapplication.until;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MobileOkHttpUtils {
    public static String OkGetArt(String url) {
        String html = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent","Mozilla/5.0 (Linux; Android 8.0.0; Nexus 6P Build/OPP3.170518.006) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Mobile Safari/537.36")
                .build();
        try (Response response = client.newCall(request).execute()) {
            //return
            html = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
