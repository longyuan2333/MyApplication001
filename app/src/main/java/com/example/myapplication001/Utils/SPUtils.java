package com.example.myapplication001.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharePreference 工具类，，把数据保存到手机存储中
 */
public class SPUtils {
    private  static SPUtils instance;
    private Context mcontext;
    //保存在手机里面的文件名
    public  String FileName="Share_data";

    public SPUtils(Context mcontext, String fileName) {
        this.mcontext = mcontext;
        FileName = fileName;
    }
    //初始化context，，获取一个全局的context （在Application里面调用）
    public static SPUtils init(Context context,String fileName){
       if(instance==null){
           synchronized (SPUtils.class){
               if(instance==null){
                   instance=new SPUtils(context,fileName);
               }
           }
       }
        return instance;
    }
    public  static SPUtils getInstance(){
        if (instance == null) {
            throw new IllegalStateException(
                    "you should can getInstance(Context context, String filename) when first time use !");
        }
        return instance;
    }
    /**
     * 保存数据，，根据拿到的数据类型保存数据
     * @param key
     * @param object  用来标明存储的key的类型（string  or int...）
     */
    public  void put(String key,Object object ){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences(FileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
       editor.commit();
    }

    /**
     * 得到保存数据的方法 ，根据默认值得到保存数据的具体类型，然后调用相对的方法来获取值
     * @param key
     * @param defaultObject  用来标明存储的key的类型（string  or int...）
     * @return
     */
    public  Object get(String key, Object defaultObject){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(FileName,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }
    /**
     *移除某个key对应的值
     * @param key
     */
    public void remove(String key){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences(FileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
       editor.commit();
    }

    /**
     * 查询某个Key是否存在
     * @param key
     * @return
     */
    public boolean contains(String key){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return  sharedPreferences.contains(key);
    }
    /**
     * 返回所有的键值对
     * @return
     */
    public Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(FileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
}
