package com.tencent.tmgp.jjzww.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.MyCenterAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2017/12/22 13:48
 * 修改人：
 * 修改时间：
 * 类描述：用户抓取记录
 */
public class MyCtachRecordActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.recode_title_tv)
    TextView recodeTitleTv;
    @BindView(R.id.mycatchrecod_recyclerview)
    RecyclerView mycatchrecodRecyclerview;
    @BindView(R.id.mycatchrecod_fail_tv)
    TextView mycatchrecodFailTv;

    private String TAG="MyCtachRecordActivity";
    private Context context=MyCtachRecordActivity.this;
    private MyCenterAdapter myCenterAdapter;
    private List<VideoBackBean> videoList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycatchrecord;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
        getVideoBackList(UserUtils.USER_ID);
        onClick();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initData() {
        myCenterAdapter = new MyCenterAdapter(context, videoList);
        mycatchrecodRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        mycatchrecodRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        mycatchrecodRecyclerview.setAdapter(myCenterAdapter);

    }

    private void onClick() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myCenterAdapter.setOnItemClickListener(new MyCenterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, RecordGameActivty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", videoList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                startActivity(new Intent(MyCtachRecordActivity.this, RecordGameTwoActivity.class));
            }
        });
    }

    private void getVideoBackList(String userId) {
        Utils.showLogE(TAG, "抓取记录参数userId=" + userId);
        HttpManager.getInstance().getVideoBackList(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                videoList = result.getData().getPlayback();
                Utils.showLogE(TAG, "抓取记录result=" + result.getMsg() + "=" + videoList.size());
                if (videoList.size() != 0) {
                    mycatchrecodFailTv.setVisibility(View.GONE);
                    //myCenterAdapter.notify(getCatchNum(removeDuplicate(videoList),videoReList));
                    myCenterAdapter.notify(videoList);
                } else {
                    Utils.showLogE("个人中心", "暂无数据");
                    mycatchrecodRecyclerview.setVisibility(View.GONE);
                    mycatchrecodFailTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void _onError(Throwable e) {
                mycatchrecodRecyclerview.setVisibility(View.GONE);
                mycatchrecodFailTv.setVisibility(View.VISIBLE);
            }
        });
    }

//    //记录数据重组   11/28 17:55
//    private List<VideoBackBean> getCatchNum(List<VideoBackBean> list, List<VideoBackBean> reList) {
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getDOLLNAME().equals(reList.get(i).getDOLLNAME())) {
//                list.get(i).setCOUNT(reList.get(i).getCOUNT());
//            } else {
//                for (int j = 0; j < reList.size(); j++) {
//                    if (reList.get(j).getDOLLNAME().equals(list.get(i).getDOLLNAME())) {
//                        list.get(i).setCOUNT(reList.get(j).getCOUNT());
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//    //记录重复赛选
//    public List<VideoBackBean> removeDuplicate(List<VideoBackBean> list) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            for (int j = list.size() - 1; j > i; j--) {
//                if (list.get(j).getDOLLNAME().equals(list.get(i).getDOLLNAME())) {
//                    list.remove(j);
//                }
//            }
//        }
//
//        return list;
//    }

}
