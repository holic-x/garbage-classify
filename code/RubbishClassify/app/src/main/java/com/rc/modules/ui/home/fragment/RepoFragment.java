package com.rc.modules.ui.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rc.R;
import com.rc.modules.model.RcUser;
import com.rc.modules.model.Rubbish;
import com.rc.modules.service.RubbishService;
import com.rc.modules.service.RubbishServiceImpl;
import com.rc.modules.ui.home.MainActivity;
import com.rc.modules.ui.home.adapter.RubbishInfoAdapter;
import com.rc.modules.ui.login.LoginActivity;
import com.rc.view.FontIconView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class RepoFragment extends Fragment implements View.OnClickListener {

    FontIconView recycled_rubbish_icon;

    FontIconView harmful_rubbish_icon;

    FontIconView kitchen_waste_rubbish_icon;

    FontIconView other_rubbish_icon;

    List<Rubbish> rubbishList;

    // 列表信息封装
    private ListView mlv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab_repo, container, false);

        // 注册图标组件，添加事件监听
        recycled_rubbish_icon = view.findViewById(R.id.recycled_rubbish_icon);
        recycled_rubbish_icon.setOnClickListener(this);

        harmful_rubbish_icon = view.findViewById(R.id.harmful_rubbish_icon);
        harmful_rubbish_icon.setOnClickListener(this);

        kitchen_waste_rubbish_icon = view.findViewById(R.id.kitchen_waste_rubbish_icon);
        kitchen_waste_rubbish_icon.setOnClickListener(this);

        other_rubbish_icon = view.findViewById(R.id.other_rubbish_icon);
        other_rubbish_icon.setOnClickListener(this);

        // 列表信息封装
        mlv = (ListView) view.findViewById(R.id.lv);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rubbish rubbish = rubbishList.get(position);
                // 按钮事件触发构建数据展示操作
                Toast.makeText(RepoFragment.this.getContext(), rubbish.getRubbishName(), Toast.LENGTH_SHORT).show();
                // 触发对话框详情展示
                showRubbishDetail(rubbish);
            }
        });

        return view;
    }

    @SneakyThrows
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recycled_rubbish_icon: {
                // 输出
                System.out.println("recycled_rubbish_icon按钮被点击了");
                Toast.makeText(this.getContext(), "正在检索，请稍后", Toast.LENGTH_SHORT).show();
                packRubbish("1");
                return;
            }
            case R.id.harmful_rubbish_icon: {
                // 输出
                System.out.println("harmful_rubbish_icon按钮被点击了");
                Toast.makeText(this.getContext(), "正在检索，请稍后", Toast.LENGTH_SHORT).show();
                packRubbish("2");
                return;
            }
            case R.id.kitchen_waste_rubbish_icon: {
                // 输出
                System.out.println("kitchen_waste_rubbish_icon按钮被点击了");
                Toast.makeText(this.getContext(), "正在检索，请稍后", Toast.LENGTH_SHORT).show();
                packRubbish("3");
                return;
            }
            case R.id.other_rubbish_icon: {
                // 输出
                System.out.println("other_rubbish_icon按钮被点击了");
                Toast.makeText(this.getContext(), "正在检索，请稍后", Toast.LENGTH_SHORT).show();
                packRubbish("4");
                return;
            }
        }
    }

    private void packRubbish(String classifyId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 封装Rubbish信息
                try {
                    getRubbish(classifyId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getRubbish(String classifyId) throws Exception {
        RubbishService rubbishService = new RubbishServiceImpl();

        // 封装查询参数，此处根据classifyId分类查找（默认4个分类，1-可回收垃圾、2-有害垃圾、3-厨余垃圾、4-其他垃圾）
        JSONObject queryCond = new JSONObject();
        queryCond.put("classifyId", classifyId);
        rubbishList = rubbishService.getRubbishByCond(queryCond);

        // 调用接口获取垃圾信息
        if (rubbishList == null || rubbishList.size() == 0) {
            System.out.println("模拟数据");
            // 若响应结果出错则封装模拟数据
            rubbishList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                Rubbish r = new Rubbish("rubbish" + i, R.drawable.default_rubbish_car);
                rubbishList.add(r);
            }
        }else{
            // 设定默认图标属性
            for (Rubbish rubbish : rubbishList) {
                rubbish.setImageId(R.drawable.default_rubbish_repo);
            }
        }

        // 调用Handle响应更新UI组件操作
        Message msg = Message.obtain();
        msg.what = 0;
        handler.sendMessage(msg);
        // RubbishInfoAdapter adapter = new RubbishInfoAdapter(this.getContext(), R.layout.repo_rubbish_item, rubbishList);
        // 将适配器上的数据传递给listView
//        mlv.setAdapter(adapter);
    }

    /**
     * 定义handle处理响应事件
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                 RubbishInfoAdapter adapter = new RubbishInfoAdapter(getContext(), R.layout.repo_rubbish_item, rubbishList);
                // 将适配器上的数据传递给listView
                mlv.setAdapter(adapter);
            } else {
                // 额外处理
//                Toast.makeText(getActivity(), "响应失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * 定义对话框封装信息详情
     */
    private void showRubbishDetail(Rubbish rubbish) {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder showDialog =
                new AlertDialog.Builder(getActivity());
        showDialog.setTitle(rubbish.getRubbishCode()+"-"+rubbish.getRubbishName()).setView(editText);
        showDialog.setMessage("垃圾介绍："+rubbish.getRemark());
        showDialog.setPositiveButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

}
