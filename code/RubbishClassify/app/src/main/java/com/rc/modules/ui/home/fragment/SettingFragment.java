package com.rc.modules.ui.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rc.R;
import com.rc.framework.utils.SharedPreferencesUtil;
import com.rc.modules.ui.home.MainActivity;
import com.rc.modules.ui.login.LoginActivity;

public class SettingFragment extends Fragment implements View.OnClickListener {


    // 登录头像
    ImageView iv_avatar;

    // 用户名
    TextView tv_nickname;

    // 描述
    TextView tv_description;

    // 注销按钮
    TextView tv_logout;

    protected SharedPreferencesUtil sp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab_setting, container, false);

        sp = SharedPreferencesUtil.getInstance(this.getContext());

        // 登录头像
        iv_avatar = (ImageView)view.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);

        // 用户名
        tv_nickname = (TextView)view.findViewById(R.id.tv_nickname);
        tv_nickname.setText((sp.getUserName()==null||sp.getUserName().equals(""))?"未登录":sp.getUserName());

        // 描述
        tv_description = (TextView)view.findViewById(R.id.tv_description);
        tv_description.setText((sp.getDescription()==null||sp.getDescription().equals(""))?"用户尚未登录":sp.getDescription());


        // 注销：添加点击监听实现注销账号
        tv_logout = (TextView)view.findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(this);

        // 返回组件
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.tv_logout):{
                // 弹出对话框，注销账号信息 通过SP操作构建登录注销操作
                showLogoutConfirmDialog();
            }
            case(R.id.iv_avatar):{
                Toast.makeText(this.getContext(), "滴滴", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void showLogoutConfirmDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SettingFragment.this.getContext());
        normalDialog.setIcon(R.drawable.confirm);
        normalDialog.setTitle("确认");
        normalDialog.setMessage("是否执行注销账号操作?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 注销账号，随后跳转登录页面
                        sp.logout();
                        Intent intent = new Intent();
                        // getActivity() 用来获取当前Activity
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取消操作
                    }
                });
        // 显示
        normalDialog.show();
    }






}
