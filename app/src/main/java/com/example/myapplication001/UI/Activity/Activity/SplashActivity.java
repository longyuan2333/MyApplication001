package com.example.myapplication001.UI.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication001.R;

public class SplashActivity extends AppCompatActivity {

    private Button button_skip;
    private Handler mhandler=new Handler();
    private Runnable mrunnable =new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        initView();
        initEvent();
        //3秒后登录
        mhandler.postDelayed(mrunnable,5000);
    }

    private void initEvent() {
        //点击跳过按钮
        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mhandler.removeCallbacks(mrunnable);
                toLoginActivity();
                finish();
            }
        });
    }
    private void initView() {
        button_skip = findViewById(R.id.btn_spalsh_skip);

    }
    private void toLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacks(mrunnable);
    }
}
