package com.example.myapplication001.Utils;


import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Author 戴着假发的程序员
 * @Company 起点编程
 * 2024/1/4 16:30
 * @Description
 */
public class OkHttpUtils {
    /**
     * 静态的成员变量，OkHttpClient对象，这个对象就是用来发送请求的对象。
     */
    private static OkHttpClient httpClient;
    /**
     * 这个类要申明为单例模式，这里使用的是饿汉模式，一上来就创建了当前类的实例对象
     */
    public static OkHttpUtils okhttpUtil = new OkHttpUtils();

    /**
     * 单例模式的要求，构造方法私有化
     */
    private OkHttpUtils(){}

    /**
     * 初始化方法，用来初始化httpClient对象
     * @return
     */
    public static void init(){
        // 初始化httpClient对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        httpClient = builder.build();
    }

    /**
     *  创建一个发送get请求的request对象
     * @param url 请求的url，Get请求的参数就是在url后面的，所以这里没有其他的参数
     * @return
     */
    private static Request createGetRequest(String url){
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.get().build();
        return request;
    };

    /**
     * 创建一个Post请求的request对象
     * @param url 请求的url
     * @param data 请求的数据。 POST请求数据不在url后，而是在一个独立的数据区域，所以请求参数要独立传递。
     * @return
     */
    private static Request createPostRequest(String url, Map<String,String> data){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Entry<String,String> entry : data.entrySet()){
            formBodyBuilder.add(entry.getKey(),entry.getValue());
        }
        FormBody formBody = formBodyBuilder.build();
        Request.Builder builder = new Request.Builder().url(url).post(formBody);
        Request request = builder.build();
        return request;
    };

    /**
     * 异步的GET请求
     * @param url 请求的url
     * @param callback  请求得到结构之后的回调方法
     */
    public static void enqueueGet(String url, Callback callback){
        Request request = createGetRequest(url);
        Call call = httpClient.newCall(request);
        // 发送请求
        call.enqueue(callback);
        // 同步请求的操作
        // Response response = call.execute();
    }

    /**
     * 异步的POST请求
     * @param url 请求的url
     * @param data 请求携带的参数
     * @param callback 等待处理结果的回调函数
     */
    public static void enqueuePost(String url,Map<String,String> data,Callback callback){
        Request request = createPostRequest(url,data);
        httpClient.newCall(request).enqueue(callback);
        // 同步请求
        // Response response = httpClient.newCall(request).execute();
    };
}
