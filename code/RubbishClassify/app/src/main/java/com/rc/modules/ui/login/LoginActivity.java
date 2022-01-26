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
import com.rc.framework.utils.SharedPreferencesUtil;
import com.rc.modules.model.RcUser;
import com.rc.modules.service.UserService;
import com.rc.modules.service.UserServiceImpl;
import com.rc.modules.ui.home.MainActivity;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edAccount;
    private EditText edPassword;
    private TextView tvLose;
    private TextView tvNew;
    private Button btnLogin;
    private Intent intent;

    // 定义SP存储用户信息
    protected SharedPreferencesUtil sp;

    private UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SP对象初始化
        sp = SharedPreferencesUtil.getInstance(this);

        // 组件注册、监听
        setContentView(R.layout.activity_login);
        edAccount = findViewById(R.id.ed_account);
        edPassword = findViewById(R.id.ed_password);
        tvLose = findViewById(R.id.tv_lose);
        tvNew = findViewById(R.id.tv_new);
        btnLogin = findViewById(R.id.btn_login);

        tvLose.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        intent = new Intent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_new: {
                // 跳转注册页面
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                return;
            }

            case R.id.btn_login: {
                String account = edAccount.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if (account.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(LoginActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
                } else {
                    login(account, password);
                    // 跳转页面（交由handle进行接管处理，待异步线程执行完成则触发handle事件）
                    // intent.setClass(LoginActivity.this, MainActivity.class);
                    // startActivity(intent);
                }
                return;
            }
        }

    }

    private void login(final String account, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 测试ip、端口是否可访问
                    Message msg = Message.obtain();
                    RcUser loginUser = userService.loginCheck(account, password, "2");
                    if (loginUser != null) {
                        msg.what = 0;
                        msg.obj = loginUser;
                        // 存储用户信息
                        sp.setUserId(loginUser.getUserId());
                        sp.setUserName(loginUser.getUserName());
                        sp.setToken(loginUser.getToken());
                        sp.setPassword(loginUser.getUserPwd());
                        sp.setDescription(loginUser.getRemark());
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
                System.out.println("登录成功....");
                // 打印信息,跳转主页面
                RcUser loginUser = (RcUser) msg.obj;
                System.out.println("登录用户：" + loginUser.getUserName());
                String welcome = getString(R.string.welcome) + loginUser.getUserName();
                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                // 校验结果，跳转主页面
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                // 额外处理
                System.out.println("登录失败，请检查信息");
                Toast.makeText(getApplicationContext(), "登录失败，请检查账号密码信息", Toast.LENGTH_SHORT).show();
            }
        }
    };

}

