package com.example.mycomicapplication.until;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    public static String OkGetArt(String url,Context context) {
        String html = null;
        if(NetCheckUtil.checkNet(context)){
//网络可用
            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                    .readTimeout(50000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                //return
                html = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
//网络不可用
            Looper.prepare();
            Toast.makeText(context,"网络不可用",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        return html;
    }

}
