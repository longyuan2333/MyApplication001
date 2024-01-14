package com.example.myapplication001.Net;

import android.telecom.Call;

import com.example.myapplication001.Utils.GsonUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
public abstract class CommonCallBack<T> extends StringCallback {
    Type mtype;

    public CommonCallBack() {
        //获取被extends 的类
        Class<? extends CommonCallBack> clazz=getClass();
        Type genericSupperclass=clazz.getGenericSuperclass();
        if(genericSupperclass instanceof  Class){
            throw  new RuntimeException(" Miss Type Params");
        }
        ParameterizedType parameterizedType= (ParameterizedType) genericSupperclass;
        //getActulTypeArguments 是一个T对象（泛型，所有是数组）
        mtype=parameterizedType.getActualTypeArguments()[0];
    }

    public void onError(Call call, Exception e, int id) {
      onError(e);
    }


    public void onResponse(String response, int id) {
        try {// 转化为json对象
            JSONObject jsonObject=new JSONObject(response);
            int resCode=jsonObject.getInt("code");
            if(resCode==0){
             //成功
                String data=jsonObject.getString("data");
                //GsonUtils 封装的Gson对象
               onSuccess((T) GsonUtils.getgson().fromJson(data,mtype));
            }else {
              //请求失败
                onError(new RuntimeException(jsonObject.getString("resultMessage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e);
        }
    }
   public abstract  void onError(Exception e);
   public abstract void onSuccess(T response);
}
