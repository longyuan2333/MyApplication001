package com.example.myapplication001.Biz;


import com.example.myapplication001.Bean.Products;
import com.example.myapplication001.Config.Config;
import com.example.myapplication001.Net.CommonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

//商品业务类
public class Productsbiz {
    public void listByPage(int currentPage, CommonCallBack<List<Products>> callback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + Config.url+"product_find")
                .tag(this)
                .addParams("currentPage", currentPage + "")
                .build()
                .execute(callback);
    }
}
