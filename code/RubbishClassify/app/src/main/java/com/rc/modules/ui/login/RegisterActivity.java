package com.rc.modules.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rc.R;
import com.rc.modules.model.RcUser;
import com.rc.modules.service.UserService;
import com.rc.modules.service.UserServiceImpl;
import com.rc.modules.ui.home.MainActivity;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edAccount;
    private EditText edPassword;
    private EditText edCPassword;
    private Button btnLogon;
    private TextView tvToLogin;
    private Intent intent;

    RequestBody requestBody;
    OkHttpClient okHttpClient;
    String res = null;

    private UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定布局
        setContentView(R.layout.activity_register);
        edAccount = findViewById(R.id.ed_account);
        edPassword = findViewById(R.id.ed_password);
        edCPassword = findViewById(R.id.ed_cpassword);
        btnLogon = findViewById(R.id.btn_register);
        tvToLogin = findViewById(R.id.btn_to_login);
        btnLogon.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                String account = edAccount.getText().toString();
                String password = edPassword.getText().toString();
                String CPassword = edCPassword.getText().toString();
                //判断一下字符字符串是否符合
                if (account.length() <= 0 || password.length() <= 0)
                    Toast.makeText(RegisterActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
                else if (!CPassword.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    // 封装参数信息，添加新用户
                    RcUser rcUser = new RcUser();
                    rcUser.setUserName(account);
                    rcUser.setUserPwd(password);
                    register(rcUser);
                }
                break;
            }
            case R.id.btn_to_login: {
                // 跳转登录页面
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 注册信息调用
     */
    private void register(RcUser rcUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = Message.obtain();
                    boolean operFlag = userService.register(rcUser);
                    if (operFlag) {
                        msg.what = 0;
                    } else {
                        msg.what = -1;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 定义handle处理响应事件
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                System.out.println("注册成功....");
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                // 打印信息,跳转登录页面
                intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                // 额外处理
                System.out.println("注册失败，请检查信息");
                Toast.makeText(getApplicationContext(), "注册失败，请稍后尝试", Toast.LENGTH_SHORT).show();
            }
        }
    };
}