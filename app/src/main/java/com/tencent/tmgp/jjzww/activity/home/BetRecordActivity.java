package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.BetRecordAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yincong on 2017/12/6 16:44
 * 修改人：
 * 修改时间：
 * 类描述：竞猜记录
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

    private String TAG = "BetRecordActivity--";
    private BetRecordAdapter betRecordAdapter;
    private List<BetRecordBean.DataListBean> list = new ArrayList<>();
    private List<BetRecordBean.DataListBean>mylist=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betrecord;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initDate();
        getGuessDetail(UserUtils.USER_ID);
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initDate() {

        betRecordAdapter = new BetRecordAdapter(this, list);
        betrecodeRecyclerview.setAdapter(betRecordAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        betrecodeRecyclerview.setLayoutManager(linearLayoutManager);
        betrecodeRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

        private void getGuessDetail(String userId){
            HttpManager.getInstance().getGuessDetail(userId, new RequestSubscriber<Result<BetRecordBean>>() {
                @Override
                public void _onSuccess(Result<BetRecordBean> loginInfoResult) {
                    if (loginInfoResult.getMsg().equals("success")){
                        list=loginInfoResult.getData().getDataList();
                        for (int i=0;i<list.size();i++){
                            if (list.get(i).getSETTLEMENT_FLAG().equals("Y")){
                                    mylist.add(list.get(i));
                                if (mylist.size()>0){
                                    betRecordAdapter.notify(mylist);
                                }else {
                                    betrecodeRecyclerview.setVisibility(View.GONE);
                                    betcecordFailTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }

                @Override
                public void _onError(Throwable e) {
                    betrecodeRecyclerview.setVisibility(View.GONE);
                    betcecordFailTv.setVisibility(View.VISIBLE);
                }
            });

        }
}
