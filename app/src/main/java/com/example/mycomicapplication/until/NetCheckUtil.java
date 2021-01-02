package com.example.mycomicapplication.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;


public class NetCheckUtil {
    public static boolean checkNet(Context context) {
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        // 判断是否具有可以用于通信渠道
        if (networks.length==0) {
            // 没有网络
            return false;
        }
        return true;
    }
}
