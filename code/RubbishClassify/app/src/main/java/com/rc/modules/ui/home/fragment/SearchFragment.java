package com.rc.modules.ui.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.rc.R;
import com.rc.modules.model.Rubbish;
import com.rc.modules.service.RubbishService;
import com.rc.modules.service.RubbishServiceImpl;
import com.rc.modules.ui.home.adapter.RubbishInfoAdapter;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    SearchView searchView;

    List<String> list;

    private LinearLayout linearLayout;

    // 列表信息封装
    private ListView searchListView;

    List<Rubbish> searchList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab_search, container, false);

        // 注册组件,添加监听事件
        searchView = view.findViewById(R.id.searchViewBtn);
        searchView.setQueryHint("请输入关键字搜索");
        //设置输入框文字颜色
        searchView.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 搜索
                search(String.valueOf(searchView.getQuery()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 搜索
                search(String.valueOf(searchView.getQuery()));
                return false;
            }
        });

        // 注册searchListView组件，添加监听器
        searchListView = view.findViewById(R.id.searchListView);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rubbish rubbish = searchList.get(position);
                // 按钮事件触发构建数据展示操作
                Toast.makeText(getContext(), rubbish.getRubbishName(), Toast.LENGTH_SHORT).show();
                // 触发对话框详情展示
                showRubbishDetail(rubbish);
            }
        });

        //初始化标签
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);

        initMarksView();
        return view;
    }

    /**
     * 初始化标签视图
     */
    private void initMarksView() {
        // 动态标签加载控制（此处暂定默认关键字）
        String[] arr = new String[]{"鱼骨头", "烟头", "一次性方便盒", "剩菜剩饭", "废弃温度计", "废旧小台灯", "过期化学物品", "垃圾"};
        list = Arrays.asList(arr);
        for (int i = 0; i < list.size(); i++) {
            // 动态加载text_view
            View view = View.inflate(getActivity(), R.layout.search_label_text_view, null);
            TextView tv = (TextView) view.findViewById(R.id.tempLabelTextView);
            tv.setText(list.get(i));
            tv.setTag(i);
            view.setTag(false);
            // 添加监听事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    TextView tv = (TextView) v.findViewById(R.id.tempLabelTextView);
                    Log.i("dxl", "标签点击事件触发");
                    if ((Boolean) v.getTag()) {
                        v.setTag(false);
                        tv.setEnabled(false);
                        Toast.makeText(getActivity(), "你取消了选择标签" + tv.getTag(), Toast.LENGTH_SHORT).show();
                    } else {
                        v.setTag(true);
                        tv.setEnabled(true);
                        // 触发搜索
                        search(String.valueOf(tv.getText()));
                        Toast.makeText(getActivity(), "你选择了标签" + tv.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            linearLayout.addView(view);
        }
    }


    private void search(String keyword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 封装Rubbish信息
                try {
                    getRubbish(keyword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getRubbish(String keyword) throws Exception {
        RubbishService rubbishService = new RubbishServiceImpl();

        // 封装查询参数，此处根据classifyId分类查找（默认4个分类，1-可回收垃圾、2-有害垃圾、3-厨余垃圾、4-其他垃圾）
        JSONObject queryCond = new JSONObject();
        queryCond.put("keyword", keyword);
        searchList = rubbishService.getRubbishByCond(queryCond);

        // 调用接口获取垃圾信息
        if (searchList == null || searchList.size() == 0) {
            System.out.println("模拟数据");
            // 若响应结果出错则封装模拟数据
            searchList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Rubbish r = new Rubbish("rubbish" + i, R.drawable.default_rubbish_car);
                searchList.add(r);
            }
        } else {
            // 设定默认图标属性
            for (Rubbish rubbish : searchList) {
                rubbish.setImageId(R.drawable.default_rubbish_repo);
            }
        }

        // 调用Handle响应更新UI组件操作
        Message msg = Message.obtain();
        msg.what = 0;
        handler.sendMessage(msg);
    }

    /**
     * 定义handle处理响应事件
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                RubbishInfoAdapter adapter = new RubbishInfoAdapter(getContext(), R.layout.repo_rubbish_item, searchList);
                // 将适配器上的数据传递给listView
                searchListView.setAdapter(adapter);
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
        showDialog.setTitle(rubbish.getRubbishCode() + "-" + rubbish.getRubbishName()).setView(editText);
        showDialog.setMessage("垃圾介绍：" + rubbish.getRemark());
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
