package com.yzy.mrbs.phalapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ZhiYuan on 2016/5/24.
 */
public class BaseThread extends Thread {
    private String url = null;
    HttpClient httpClient = new DefaultHttpClient();

    public void BaseThread(String s) {
        this.url = s;
    }

    @Override
    public void run() {
        // 创建一个HttpGet对象
        HttpGet get = new HttpGet(url);
        try {
            // 发送GET请求
            HttpResponse httpResponse = httpClient.execute(get);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


