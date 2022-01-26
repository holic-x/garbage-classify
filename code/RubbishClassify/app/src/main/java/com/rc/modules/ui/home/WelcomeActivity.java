package com.rc.modules.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rc.R;
import com.rc.modules.ui.login.LoginActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //去掉顶部标题
        Objects.requireNonNull(getSupportActionBar()).hide();
        //去掉最上面时间、电量以及其他信息，此方法以及上面方法可以实现欢迎页全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startIndexActivity();
    }

    private void startIndexActivity() {
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                // 页面跳转
                Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        };
        // 设置延迟跳转时间
        Timer timer = new Timer();
        timer.schedule(delayTask, 3000);// 延时两秒执行 run 里面的操作
    }
}
