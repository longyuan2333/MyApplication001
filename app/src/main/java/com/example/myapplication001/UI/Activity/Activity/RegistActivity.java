package com.example.myapplication001.UI.Activity.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;

import com.example.myapplication001.Bean.User;
import com.example.myapplication001.Biz.Userbiz;
import com.example.myapplication001.Net.CommonCallBack;
import com.example.myapplication001.R;
import com.example.myapplication001.Utils.Toas;

import okhttp3.Call;

public class RegistActivity extends BaseActivity {
    EditText edit_username,edit_password,edit_repassword;
    Button button_regist;
    Userbiz userbiz=new Userbiz();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_view);
        initView();
        initEvent();
        setUpToorBar();
        setTitle("注册");
    }
    //事件处理
    private void initEvent() {
        button_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=edit_username.getText().toString();
                final String password=edit_password.getText().toString();
                final String repassword=edit_repassword.getText().toString();
                if(!TextUtils.equals(password,repassword)){
                    Toas.ShowToast("两次输入的密码不一致，请从新输入！");
                    return;
                }
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toas.ShowToast("账号或者密码不能为空");
                    return;
                }
                startLodingProgress();
                userbiz.Register(username, password, new CommonCallBack<User>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toas.ShowToast(e.getMessage());
                       stopLodingProgress();
                    }
                    @Override
                    public void onSuccess(User response) {
                        stopLodingProgress();
                        Toas.ShowToast("注册成功,用户名为："+response.getUsername());
                        LoginActivity.lanuch(RegistActivity.this,response.getUsername(),response.getPassword());
                        finish();
                    }
                });
            }
        });
    }

    // 初始化控件
    private void initView() {
         edit_username=findViewById(R.id.edit_phoneNumber);
         edit_password=findViewById(R.id.edit_password);
         edit_repassword=findViewById(R.id.edit_repassword);
         button_regist=findViewById(R.id.regist_btn);
    }
}
