package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.RecordAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class RecordActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.recode_recyclerview)
    RecyclerView recodeRecyclerview;
    @BindView(R.id.recode_title_tv)
    TextView recodeTitleTv;
    @BindView(R.id.cecord_fail_tv)
    TextView cecordFailTv;

    private RecordAdapter recordAdapter;
    private String userName = "";
    private List<VideoBackBean> listVideo = new ArrayList<>();

    //我的主娃娃记录
    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initdata();
        onClick();
        getVideoBackList(userName);
    }

    private void initdata() {
        userName = UserUtils.NickName;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recodeRecyclerview.setLayoutManager(linearLayoutManager);
        recodeRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recordAdapter = new RecordAdapter(RecordActivity.this, listVideo);
        recodeRecyclerview.setAdapter(recordAdapter);

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    private void onClick() {
        recordAdapter.setmOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }


    private void getVideoBackList(String userName) {
        HttpManager.getInstance().getVideoBackList(userName, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                listVideo = result.getData().getPlayback();
                Utils.showLogE("video记录列表", "result=" + result.getMsg()+"="+listVideo.size());
                if(listVideo.size()!=0) {
                    cecordFailTv.setVisibility(View.GONE);
                    recordAdapter.notify(listVideo);
                }else {
                    recodeRecyclerview.setVisibility(View.GONE);
                    cecordFailTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


    public RecordAdapter.OnItemClickListener onItemClickListener = new RecordAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(RecordActivity.this, VideoPlayBackActivity.class);
            intent.putExtra("time", listVideo.get(position).getCREATETIME());
            startActivity(intent);
        }
    };
}
