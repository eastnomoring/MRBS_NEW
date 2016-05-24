package com.yzy.mrbs.util;

import android.util.Log;

import com.yzy.mrbs.base.C;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 网络通信类
 * Created by ZhiYuan on 2016/4/27.
 */
public class AppClient {
    // 压缩配置
    final private static int CS_NONE = 0;
    final private static int CS_GZIP = 1;

    // 必要类属性
    private String apiUrl;
    private HttpParams httpParams;
    private HttpClient httpClient;
    private int timeoutConnection = 10000;
    private int timeoutSocket = 10000;
    private int compress = CS_NONE;

    // 默认字符集为 utf8
    private String charset = HTTP.UTF_8;

    public AppClient (String url) {
        initClient(url);
    }

    public AppClient (String url, String charset, int compress) {
        initClient(url);
        this.charset = charset; // set charset
        this.compress = compress; // set strategy
    }

    /**
     * httpClient对象属性的初始化方法
     * @param url
     */
    private void initClient (String url) {
        // 初始化 API URL 地址
        this.apiUrl = C.api.base + url;
        //自动添加 Session ID
//        String apiSid = AppUtil.getSessionId();
//        if (apiSid != null && apiSid.length() > 0) {
//            this.apiUrl += "?sid=" + apiSid;
//        }
        // 设置网络超时
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
        // 初始化 HttpClient 对象
        httpClient = new DefaultHttpClient(httpParams);
    }

    /**
     * 用于支持使用CMWAP网络接入方式的用户上网
     */
    public void useWap () {
        //与支持WAP上网方式有关的逻辑
        HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }

    /**
     * 处理HTTP协议的GET请求
     * @return
     * @throws Exception
     */
    public String get () throws Exception {
        try {
            //初始化GET请求对象
//            HttpGet httpGet = headerFilter(new HttpGet(this.apiUrl));
            HttpGet httpGet = new HttpGet(this.apiUrl);
            //记录GET请求发送日志
            Log.w("AppClient.get.url", this.apiUrl);
            // 发送GET请求
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String httpResult = resultFilter(httpResponse.getEntity());
                Log.w("AppClient.get.result", httpResult);
                return httpResult;
            } else {
                return null;
            }
        } catch (ConnectTimeoutException e) {
            throw new Exception(C.err.network);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理HTTP协议的POST请求
     * @param urlParams
     * @return
     * @throws Exception
     */
    public String post (HashMap urlParams) throws Exception {
        try {
            //初始化POST请求对象
            HttpPost httpPost = headerFilter(new HttpPost(this.apiUrl));
//            HttpPost httpPost = new HttpPost(this.apiUrl);
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            // 构造POST请求参数
            Iterator it = urlParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                postParams.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            // 设置POST请求参数编码
            if (this.charset != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(postParams, this.charset));
            } else {
                httpPost.setEntity(new UrlEncodedFormEntity(postParams));
            }
            //记录POST请求发送日志
            Log.w("AppClient.post.url", this.apiUrl);
            Log.w("AppClient.post.data", postParams.toString());

            //发送POST请求
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String httpResult = resultFilter(httpResponse.getEntity());
                //记录POST请求结果日志
                Log.w("AppClient.post.result", httpResult);
                //返回POST请求结果
                return httpResult;
            } else {
                return null;
            }
        } catch (ConnectTimeoutException e) {
            throw new Exception(C.err.network);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为GET请求对象设置请求头
     * @param httpGet
     * @return httpGet
     */
    private HttpGet headerFilter (HttpGet httpGet) {
        switch (this.compress) {
            case CS_GZIP:
                httpGet.addHeader("Accept-Encoding", "gzip");
                break;
            default :
                break;
        }
        return httpGet;
    }

    /**
     * 为POST请求对象设置请求头
     * @param httpPost
     * @return
     */
    private HttpPost headerFilter (HttpPost httpPost) {
        switch (this.compress) {
            case CS_GZIP:
                httpPost.addHeader("Accept-Encoding", "gzip");
                break;
            default :
                break;
        }
        return httpPost;
    }

    /**
     * 对请求结果进行GZIP解码处理
     * @param entity
     * @return
     */
    private String resultFilter(HttpEntity entity){
        String result = null;
        try {
            switch (this.compress) {
                case CS_GZIP:
                    result = AppUtil.gzipToString(entity);
                    break;
                default :
                    result = EntityUtils.toString(entity);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
