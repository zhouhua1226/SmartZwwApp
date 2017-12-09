package com.tencent.tmgp.jjzww.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.List_ViewPagerAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/25.
 */
public class MyListFragment extends BaseFragment {
    @BindView(R.id.viewpager_left_tv)
    TextView viewpagerLeftTv;
    @BindView(R.id.viewpager_menu_left_img)
    ImageView viewpagerMenuLeftImg;
    @BindView(R.id.viewpager_right_tv)
    TextView viewpagerRightTv;
    @BindView(R.id.viewpager_menu_right_img)
    ImageView viewpagerMenuRightImg;
    @BindView(R.id.pager_list)
    ViewPager pagerList;
    //Unbinder unbinder;
    private List_ViewPagerAdapter listViewPagerAdapter;
    private ListRecordFragment listRecordFragment;
    private ListRankFragment listFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> list;
    private int tag = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        firstInit();
    }

    public void initView() {
        fragmentManager = getFragmentManager();
        list = new ArrayList<Fragment>();
        listRecordFragment = new ListRecordFragment();
        listFragment = new ListRankFragment();
        list.add(listRecordFragment);
        list.add(listFragment);
        listViewPagerAdapter = new List_ViewPagerAdapter(fragmentManager, list);
        pagerList.setAdapter(listViewPagerAdapter);
        pagerList.setOnPageChangeListener(new PagerChangeListener());
    }

    //第一次时的页面
    public void firstInit() {
        viewpagerLeftTv.setText("最近抓中记录");
        viewpagerRightTv.setText("排行榜");
        if (tag == 0) {
            viewpagerLeftTv.setTextColor(getResources().getColor(R.color.pink));
            viewpagerMenuLeftImg.setBackgroundColor(getResources().getColor(R.color.pink));
        } else if (tag == 1) {
            viewpagerRightTv.setTextColor(getResources().getColor(R.color.gray));
            viewpagerMenuRightImg.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @OnClick({R.id.viewpager_left_tv, R.id.viewpager_right_tv})
    public void onViewClicked(View view) {
        clear();
        switch (view.getId()) {
            case R.id.viewpager_left_tv:
                pagerList.setCurrentItem(0);
                viewpagerLeftTv.setTextColor(getResources().getColor(R.color.pink));
                viewpagerMenuLeftImg.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
            case R.id.viewpager_right_tv:
                pagerList.setCurrentItem(1);
                viewpagerRightTv.setTextColor(getResources().getColor(R.color.pink));
                viewpagerMenuRightImg.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
        }
    }


    //滑动监听事件
    public class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagerChange(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    //重置文本颜色
    public void clear() {
        viewpagerLeftTv.setTextColor(getResources().getColor(R.color.gray));
        viewpagerMenuLeftImg.setBackgroundColor(getResources().getColor(R.color.gray));
        viewpagerRightTv.setTextColor(getResources().getColor(R.color.gray));
        viewpagerMenuRightImg.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    //滑动时改变颜色
    public void pagerChange(int i) {
        clear();
        switch (i) {
            case R.id.viewpager_left_tv:
            case 0:
                tag = 0;
                pagerList.setCurrentItem(0);
                viewpagerLeftTv.setTextColor(getResources().getColor(R.color.pink));
                viewpagerMenuLeftImg.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
            case R.id.viewpager_right_tv:
            case 1:
                tag = 1;
                pagerList.setCurrentItem(1);
                viewpagerRightTv.setTextColor(getResources().getColor(R.color.pink));
                viewpagerMenuRightImg.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
        }
    }
}
