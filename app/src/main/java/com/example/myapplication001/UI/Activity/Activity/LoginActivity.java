package com.example.myapplication001.UI.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication001.Bean.User;
import com.example.myapplication001.Biz.Userbiz;
import com.example.myapplication001.Net.LoginCallBack;
import com.example.myapplication001.R;
import com.example.myapplication001.UserInfoViewHolder;
import com.example.myapplication001.Utils.Toas;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import okhttp3.Call;

public class LoginActivity extends BaseActivity {
    private EditText edit_username,edit_password;
    private TextView login_regist_tv;
    private Button login_btn;
    public Userbiz userbiz=new Userbiz();
    public static String  keyUsername = "key_username";
    public static String  keyPassword = "key_password";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        initView();
        initEvent();
        initIntent();
    }
    private void initIntent() {
        Intent intent=getIntent();
        if(intent!=null){
            String username=intent.getStringExtra(keyUsername);
            if(username!=null){
                edit_username.setText(username);
            }
            String password=intent.getStringExtra(keyPassword);
            if(password!=null){
                edit_password.setText(password);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //清除cookie
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    //事件处理
    private void initEvent() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=edit_username.getText().toString();
                final String password=edit_password.getText().toString();
                // 检查登录是否成功
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)) {
                    Toas.ShowToast("账号或密码不能为空");
                    //一定要return 防止继续执行
                    return;
                }
                    //封装在BaseActivity中的方法
                    startLodingProgress();
                    userbiz.Login(username, password, new LoginCallBack<User>() {

                        public void onError(Exception e) {
                            Toas.ShowToast(e.getMessage());
                            return;
                        }
                        @Override
                        public void onSuccess(User response) {
                         Toas.ShowToast("登录成功");
                         //保存用户信息道本地
                         UserInfoViewHolder.getInstance().setUser(response);
                         toOrderActivity();
                            finish();
                        }
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                    });


            }
        });
        login_regist_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegistActivity();
            }
        });
    }

    private void toOrderActivity() {
        //跳转页面
        startActivity(new Intent(LoginActivity.this, OrderActivity.class));
        finish();
    }
    //跳转到注册页面
    private void toRegistActivity() {
        //跳转页面
        startActivity(new Intent(LoginActivity.this, RegistActivity.class));
    }

    private void initView() {
        edit_username = findViewById(R.id.login_edit_1);
        edit_password=findViewById(R.id.login_edit_2);
        login_btn = findViewById(R.id.login_btn);
        login_regist_tv = findViewById(R.id.login_regist);
    }

    public static void lanuch(RegistActivity registActivity, String username, String password) {
        Intent intent=new Intent(registActivity,LoginActivity.class);
        intent.putExtra(keyUsername,username);
        intent.putExtra(keyPassword,password);
        registActivity.startActivity(intent);
    }
}
