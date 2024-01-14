package com.example.myapplication001;

import android.app.Application;

import com.example.myapplication001.Utils.SPUtils;
import com.example.myapplication001.Utils.Toas;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 使用Application 获取全局变量
 */
public class mApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toas.init(this);
        SPUtils.init(this,"xyuRestarent_share.pref");
        //okHttpUtils的使用配置
        CookieJarImpl cookieJar=new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                //连接的时间
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
