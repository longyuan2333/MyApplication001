package com.example.myapplication001.UI.Activity.Activity;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication001.R;


public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
    }
    //停止加载
    protected  void stopLodingProgress(){
        if(progressDialog!=null&&progressDialog.isShowing()){
           //隐藏
            progressDialog.dismiss();
        }
    }
    //开始加载
    protected  void startLodingProgress(){
        //展示
        progressDialog.show();
    }
    protected void setUpToorBar() {
        toolbar = findViewById(R.id.id_toorBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在destroy时候确保progressDialog对象为null
        stopLodingProgress();
        progressDialog=null;
    }
}
