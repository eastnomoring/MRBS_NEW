package com.yzy.mrbs.activity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseUiUser;
import com.yzy.mrbs.base.C;
import com.yzy.mrbs.util.AppClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class BookSearch extends BaseUiUser {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrbs_activity_book_search);
//        showLoadBar();
//        openDialog(savedInstanceState);


    }
    public class HttpUtil {
        // 创建HttpClient对象
        public  HttpClient httpClient = new DefaultHttpClient();
        public static final String BASE_URL = "http://192.168.1.106:8001/index/login";
        /**
         *
         * @param url 发送请求的URL
         * @return 服务器响应字符串
         * @throws Exception
         */
        public  String getRequest(String url) throws Exception {
            // 创建HttpGet对象。
            HttpGet get = new HttpGet(url);
            // 发送GET请求
            HttpResponse httpResponse = httpClient.execute(get);
            // 如果服务器成功地返回响应
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获取服务器响应字符串
                String result = EntityUtils.toString(httpResponse.getEntity());
                return result;
            } else {
                Log.d("服务器响应代码", (new Integer(httpResponse.getStatusLine()
                        .getStatusCode())).toString());
                return null;
            }
        }

        /**
         *
         * @param url
         *            发送请求的URL
         * @param
         * @return 服务器响应字符串
         * @throws Exception
         */
        public  String postRequest(String url, Map<String, String> rawParams)
                throws Exception {
            // 创建HttpPost对象。
            HttpPost post = new HttpPost(url);
            // 如果传递参数个数比较多的话可以对传递的参数进行封装
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String key : rawParams.keySet()) {
                // 封装请求参数
                params.add(new BasicNameValuePair(key, rawParams.get(key)));
            }
            // 设置请求参数
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 发送POST请求
            HttpResponse httpResponse = httpClient.execute(post);
            // 如果服务器成功地返回响应
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获取服务器响应字符串
                String result = EntityUtils.toString(httpResponse.getEntity());
                return result;
            }
            return null;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
