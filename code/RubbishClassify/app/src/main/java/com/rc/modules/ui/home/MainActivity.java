package com.rc.modules.ui.home;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rc.R;
import com.rc.modules.ui.home.fragment.IndexFragment;
import com.rc.modules.ui.home.fragment.RepoFragment;
import com.rc.modules.ui.home.fragment.SearchFragment;
import com.rc.modules.ui.home.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页设置：
 * 设定index-垃圾分类首页、knowledgeRepo-知识库、search-搜索列表、setting-设置页
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout mTabIndex;
    private LinearLayout mTabRepo;
    private LinearLayout mTabSearch;
    private LinearLayout mTabSetting;

    //四个Tab对应的ImageButton
    private ImageButton mImgIndex;
    private ImageButton mImgRepo;
    private ImageButton mImgSearch;
    private ImageButton mImgSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new IndexFragment());
        mFragments.add(new RepoFragment());
        mFragments.add(new SearchFragment());
        mFragments.add(new SettingFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        // 设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        // 设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabIndex.setOnClickListener(this);
        mTabRepo.setOnClickListener(this);
        mTabSearch.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabIndex = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabRepo = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabSearch = (LinearLayout) findViewById(R.id.id_tab_address);
        mTabSetting = (LinearLayout) findViewById(R.id.id_tab_setting);

        mImgIndex = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgRepo = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mImgSearch = (ImageButton) findViewById(R.id.id_tab_address_img);
        mImgSetting = (ImageButton) findViewById(R.id.id_tab_setting_img);

    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                selectTab(0);
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                break;
            case R.id.id_tab_address:
                selectTab(2);
                break;
            case R.id.id_tab_setting:
                selectTab(3);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgIndex.setImageResource(R.mipmap.tab_index_pressed);
                break;
            case 1:
                mImgRepo.setImageResource(R.mipmap.tab_repo_pressed);
                break;
            case 2:
                mImgSearch.setImageResource(R.mipmap.tab_search_pressed);
                break;
            case 3:
                mImgSetting.setImageResource(R.mipmap.tab_setting_pressed);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgIndex.setImageResource(R.mipmap.tab_index_default);
        mImgRepo.setImageResource(R.mipmap.tab_repo_default);
        mImgSearch.setImageResource(R.mipmap.tab_search_default);
        mImgSetting.setImageResource(R.mipmap.tab_setting_default);
    }
}