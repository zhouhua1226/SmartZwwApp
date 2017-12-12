package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.BetRecordAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2017/12/6 16:44
 * 修改人：
 * 修改时间：
 * 类描述：投注记录
 */
public class BetRecordActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.recode_title_tv)
    TextView recodeTitleTv;
    @BindView(R.id.betrecode_recyclerview)
    RecyclerView betrecodeRecyclerview;
    @BindView(R.id.betcecord_fail_tv)
    TextView betcecordFailTv;

    private String TAG="BetRecordActivity--";
    private BetRecordAdapter betRecordAdapter;
    private List<BetRecordBean> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betrecord;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        getDate();
        initDate();
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initDate(){
        betRecordAdapter = new BetRecordAdapter(this, list);
        betrecodeRecyclerview.setAdapter(betRecordAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        betrecodeRecyclerview.setLayoutManager(linearLayoutManager);
        betrecodeRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getDate(){
        int playId[]={2,4,6,7,8,21,25,29,30,45};
        String time[]={"2017/12/5  08:22:20","2017/12/5  08:25:50","2017/12/5  10:12:10","2017/12/5  12:42:08",
                        "2017/12/5  12:52:43","2017/12/5  13:55:55","2017/12/5  21:04:20","2017/12/5  21:08:34",
                        "2017/12/5  22:50:11","2017/12/5  23:09:25"};
        String room[]={"皮卡丘","我爱罗","杰尼龟","超梦","皮卡丘","妙蛙种子",
                        "杰尼龟","杰尼龟","皮卡丘","超梦","宇智波鼬","史迪奇"};
        String money[]={"+40","+60","-50","-70","+80","+30","-20","+40","+90","-70"};
        for(int i=0;i<playId.length;i++){
            BetRecordBean betRecordBean=new BetRecordBean();
            betRecordBean.setPLAYID(String.valueOf(playId[i]));
            betRecordBean.setBETTIME(time[i]);
            betRecordBean.setDOLLNAME(room[i]);
            betRecordBean.setBETMONEY(money[i]);
            list.add(betRecordBean);
        }


    }


}
