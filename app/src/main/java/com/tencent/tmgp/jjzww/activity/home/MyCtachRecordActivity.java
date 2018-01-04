package com.tencent.tmgp.jjzww.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.QuizInstrictionDialog;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;
import com.tencent.tmgp.jjzww.view.SureCancelDialog;

import java.io.InputStream;
import java.io.Serializable;
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
    @BindView(R.id.mycatchrecod_dialog_image)
    ImageView mycatchrecodDialogImage;
    @BindView(R.id.mycatchrecod_top_layout)
    RelativeLayout mycatchrecodTopLayout;
    @BindView(R.id.mycatchrecod_fx_image)
    ImageView mycatchrecodFxImage;
    @BindView(R.id.mycatchrecod_fx_layout)
    LinearLayout mycatchrecodFxLayout;
    @BindView(R.id.mycatchrecod_qx_image)
    ImageView mycatchrecodQxImage;
    @BindView(R.id.mycatchrecod_qx_layout)
    LinearLayout mycatchrecodQxLayout;
    @BindView(R.id.mycatchrecod_fhsure_image)
    ImageView mycatchrecodFhsureImage;
    @BindView(R.id.mycatchrecod_dhsure_image)
    ImageView mycatchrecodDhsureImage;
    @BindView(R.id.mycatchrecod_selectnum_tv)
    TextView mycatchrecodSelectnumTv;
    @BindView(R.id.mycatchrecod_bottom_layout)
    RelativeLayout mycatchrecodBottomLayout;

    private String TAG = "MyCtachRecordActivity";
    private Context context = MyCtachRecordActivity.this;
    private boolean isSelest=false;
    private QuizInstrictionDialog quizInstrictionDialog;
    private MyCenterAdapter myCenterAdapter;
    private List<Boolean> isList=new ArrayList<>();
    private List<Boolean> islist=new ArrayList<>();
    private List<Integer> num=new ArrayList<>();
    private List<VideoBackBean> videoList = new ArrayList<>();
    private List<VideoBackBean> selectList=new ArrayList<>();
    private StringBuffer stringId = new StringBuffer("");
    private StringBuffer stringDollId = new StringBuffer("");

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
        mycatchrecodRecyclerview.setAdapter(myCenterAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mycatchrecodRecyclerview.setLayoutManager(linearLayoutManager);
        mycatchrecodRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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

                if(islist.get(position)){
                    Intent intent = new Intent(context, RecordGameActivty.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record", videoList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
//                    view.setBackgroundResource(R.drawable.mycatchrecord_select);
//                    num.add(position);
//                    selectList.add(videoList.get(position));
//                    mycatchrecodSelectnumTv.setText("已选中"+selectList.size()+"个娃娃");
                    if(isList.get(position)){
//                        view.setBackgroundResource(R.drawable.mycatchrecord_unselect);
//                        isList.set(position,false);
//                        num.remove(num.indexOf(position));
//                        selectList.remove(num.indexOf(position));
//                        mycatchrecodSelectnumTv.setText("已选中"+selectList.size()+"个娃娃");
                    }else {
                        view.setBackgroundResource(R.drawable.mycatchrecord_select);
                        isList.set(position,true);
                        num.add(position);
                        selectList.add(videoList.get(position));
                        mycatchrecodSelectnumTv.setText("已选中"+selectList.size()+"个娃娃");
                    }


                }

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
                    for (int i=0;i<videoList.size();i++){
                        if(videoList.get(i).getPOST_STATE().equals("0")){
                            islist.add(false);
                            isList.add(false);
                        }else {
                            islist.add(true);
                            isList.add(true);

                        }
                    }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mycatchrecod_dialog_image, R.id.mycatchrecod_fx_layout,
                R.id.mycatchrecod_qx_layout, R.id.mycatchrecod_fhsure_image,
                R.id.mycatchrecod_dhsure_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mycatchrecod_dialog_image:
                //邮寄说明对话框
                quizInstrictionDialog = new QuizInstrictionDialog(this, R.style.easy_dialog_style);
                quizInstrictionDialog.show();
                quizInstrictionDialog.setTitle("娃娃邮寄说明");
                quizInstrictionDialog.setContent(Utils.readAssetsTxt(this, "consignintroduce"));
                break;
            case R.id.mycatchrecod_fx_layout:
                //反选
                break;
            case R.id.mycatchrecod_qx_layout:
                //全选
                break;
            case R.id.mycatchrecod_fhsure_image:
                //发货
                final int lengths=selectList.size();
                Log.e(TAG,"发货娃娃数量="+lengths);
                if(lengths>0) {
                    Intent intent = new Intent(this, ConsignmentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record", (Serializable) selectList);//序列化,要注意转化(Serializable)
                    intent.putExtras(bundle);//发送数据
                    startActivityForResult(intent,1);
                    finish();
                }else {
                    MyToast.getToast(getApplicationContext(),"请至少选择一个娃娃！").show();
                }
                break;
            case R.id.mycatchrecod_dhsure_image:
                //兑换
                final int length=selectList.size();
                Log.e(TAG,"兑换娃娃数量="+length);
                if(length>0) {
                    SureCancelDialog sureCancelDialog = new SureCancelDialog(this, R.style.easy_dialog_style);
                    sureCancelDialog.setCancelable(false);
                    sureCancelDialog.show();
                    sureCancelDialog.setDialogTitle("确定要将这些娃娃兑换成游戏币继续抓取吗？");
                    sureCancelDialog.setDialogResultListener(new SureCancelDialog.DialogResultListener() {
                        @Override
                        public void getResult(int resultCode) {
                            if (1 == resultCode) {// 确定
                                for(int i=0;i<length;i++){
                                    if(i==0){
                                        stringId.append(selectList.get(i).getID());
                                        stringDollId.append(selectList.get(i).getDOLLID());
                                    }else {
                                        stringId.append(","+selectList.get(i).getID());
                                        stringDollId.append(selectList.get(i).getDOLLID());
                                    }
                                }
                                Log.e(TAG,"兑换娃娃编号="+stringId);
                                getExChangeWWB(String.valueOf(stringId),String.valueOf(stringDollId), length+"", UserUtils.USER_ID);
                            } else {
                                MyToast.getToast(getApplicationContext(), "兑换取消!").show();
                            }
                        }
                    });
                }else {
                    MyToast.getToast(getApplicationContext(),"请至少选择一个娃娃！").show();
                }

                break;


            default:
                break;
        }
    }

    private void getExChangeWWB(String id,String dollId,String number,String userId){
        HttpManager.getInstance().getExChangeWWB(id, dollId, number, userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG,"兑换结果="+loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")) {
                    UserUtils.UserBalance=loginInfoResult.getData().getAppUser().getBALANCE();
//                    videoList=loginInfoResult.getData().getPlayback();
//                    myCenterAdapter.notify(videoList);
                    MyToast.getToast(getApplicationContext(), "兑换成功!").show();
                    finish();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


    /*****
     * 接受发货返回的数据时调用
     * ****/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        videoList= (List<VideoBackBean>) data.getExtras().getSerializable("record");
        // 根据返回码的不同，设置参数
        if (requestCode == 1) {
            myCenterAdapter.notify(videoList);
        }
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
