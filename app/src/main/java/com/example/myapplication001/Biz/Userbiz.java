package com.example.myapplication001.Biz;

import com.example.myapplication001.Bean.User;
import com.example.myapplication001.Config.Config;
import com.example.myapplication001.Net.CommonCallBack;
import com.example.myapplication001.Net.LoginCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

//用户业务类
public class Userbiz {
    //注册
    public   void Register(String username,String password , CommonCallBack<User> commonCallBack){
        OkHttpUtils.post()
                .url(Config.baseUrl + Config.urlSave+"username"+"="+username+"&"+"password"+"="+password)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallBack);
    }
    //登录
    public void Login(String username, String password, LoginCallBack<User> LoginCallBack){
        OkHttpUtils.post()
                .url(Config.baseUrl + Config.urlQueryByPage+"username"+"="+username+"&"+"password"+"="+password)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(LoginCallBack);

    }
}
