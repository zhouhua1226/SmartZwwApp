package com.tencent.tmgp.jjzww.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhouh on 2017/9/27.
 */
public class RankFragment extends BaseFragment{
    @BindView(R.id.rankVp)
    ViewPager rankVp;
    @BindView(R.id.ranktabLy)
    TabLayout ranktabLy;

    private ListRankFragment listRankFragment;
    private ListRecordFragment listRecordFragment;
    private static final String[] titles = {"最近抓中记录","排行榜"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        listRankFragment = new ListRankFragment();
        listRecordFragment = new ListRecordFragment();
        fragments.add(listRecordFragment);
        fragments.add(listRankFragment);
        rankVp.setAdapter(new TabFragmentAdapter(fragments, titles, getChildFragmentManager(), getContext()));
        rankVp.setOffscreenPageLimit(titles.length);
        ranktabLy.setupWithViewPager(rankVp);
        ranktabLy.setTabTextColors(Color.BLACK, getResources().getColor(R.color.pink));
    }
}
