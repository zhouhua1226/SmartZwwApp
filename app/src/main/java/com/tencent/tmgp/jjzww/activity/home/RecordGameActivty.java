package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.ConsigneeBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.SureCancelDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class RecordGameActivty extends BaseActivity {
    //未申请发货
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.gamemoney_button)
    Button gamemoneyButton;
    @BindView(R.id.shipments_button)
    Button shipmentsButton;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.times_tv)
    TextView timesTv;
    @BindView(R.id.results_tv)
    TextView resultsTv;
    @BindView(R.id.mydoll_num_tv)
    TextView mydollNumTv;
    @BindView(R.id.mydoll_id_tv)
    TextView mydollIdTv;
    @BindView(R.id.mydoll_state_tv)
    TextView mydollStateTv;
    @BindView(R.id.mydoll_exchangenum_tv)
    TextView mydollExchangenumTv;
    @BindView(R.id.text_tv)
    TextView textTv;
    @BindView(R.id.nonesend_layout)
    RelativeLayout nonesendLayout;
    @BindView(R.id.sendname_tv)
    TextView sendnameTv;
    @BindView(R.id.sendaddress_tv)
    TextView sendaddressTv;
    @BindView(R.id.sendremark_tv)
    TextView sendremarkTv;
    @BindView(R.id.sendphoto_tv)
    TextView sendphotoTv;
    @BindView(R.id.send_layout)
    RelativeLayout sendLayout;
    @BindView(R.id.exchanged_tv)
    TextView exchangedTv;

    private String TAG="RecordGameActivty--";
    private VideoBackBean videoBackBean;
    private List<VideoBackBean> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recordgame;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        videoBackBean = (VideoBackBean) getIntent().getExtras().getSerializable("record");
        list.add(videoBackBean);
    }


    private void initData() {
        nameTv.setText(videoBackBean.getDOLL_NAME());
        timesTv.setText(Utils.getTime(videoBackBean.getCAMERA_DATE()));
        mydollNumTv.setText("1");
        mydollExchangenumTv.setText(videoBackBean.getCONVERSIONGOLD() + "");
        mydollIdTv.setText(videoBackBean.getID() + "");
        getViewChange();
        Glide.with(this)
                .load(UrlUtils.APPPICTERURL + videoBackBean.getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(this))
                .into(titleImg);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }

    @OnClick({R.id.image_back, R.id.image_service, R.id.gamemoney_button, R.id.shipments_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.image_service:
                startActivity(new Intent(RecordGameActivty.this,MyCtachRecordActivity.class));
                break;
            case R.id.gamemoney_button:
                //兑换游戏币
                SureCancelDialog sureCancelDialog=new SureCancelDialog(this,R.style.easy_dialog_style);
                sureCancelDialog.setCancelable(false);
                sureCancelDialog.show();
                sureCancelDialog.setDialogTitle("确定要将这个娃娃兑换成游戏币继续抓取吗？");
                sureCancelDialog.setDialogResultListener(new SureCancelDialog.DialogResultListener() {
                    @Override
                    public void getResult(int resultCode) {
                        if (1 == resultCode) {// 确定
                            getExChangeWWB(String.valueOf(videoBackBean.getID()),videoBackBean.getDOLLID(),"1", UserUtils.USER_ID);
                        }else {
                            MyToast.getToast(getApplicationContext(),"兑换取消!").show();
                        }
                    }
                });

                break;
            case R.id.shipments_button:
                //申请发货
                Intent intent = new Intent(this, ConsignmentActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("sqfh", videoBackBean);
                bundle.putSerializable("record", (Serializable) list);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
                finish();
                break;
        }
    }

    private void getViewChange(){
        //0:寄存   1:发货   2:兑换游戏币
        if (videoBackBean.getPOST_STATE().equals("0")) {
            mydollStateTv.setText("寄存中");
            nonesendLayout.setVisibility(View.VISIBLE);
            sendLayout.setVisibility(View.GONE);
            exchangedTv.setVisibility(View.GONE);
        } else if(videoBackBean.getPOST_STATE().equals("1")){
            mydollStateTv.setText("已发货");
            nonesendLayout.setVisibility(View.GONE);
            sendLayout.setVisibility(View.VISIBLE);
            exchangedTv.setVisibility(View.GONE);
            ConsigneeBean consigneeBean=Utils.getConsigneeBean(videoBackBean.getSENDGOODS());
            if (consigneeBean != null) {
                sendnameTv.setText("收货人：" + consigneeBean.getName());
                sendphotoTv.setText(consigneeBean.getPhone());
                sendaddressTv.setText("地址：" + consigneeBean.getAddress());
                if (consigneeBean.getRemark() != null) {
                    sendremarkTv.setText("备注：" + consigneeBean.getRemark());
                }
            }

        } else if(videoBackBean.getPOST_STATE().equals("2")){
            mydollStateTv.setText("已兑换");
            nonesendLayout.setVisibility(View.GONE);
            sendLayout.setVisibility(View.GONE);
            exchangedTv.setVisibility(View.VISIBLE);
        }
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
        list= (List<VideoBackBean>) data.getExtras().getSerializable("record");
        if(list.size()>0){
            videoBackBean=list.get(0);
        }
        // 根据返回码的不同，设置参数
        if (requestCode == 0) {
            getViewChange();
        }
    }

    private void getExChangeWWB(String id,String dollId,String number,String userId){
        HttpManager.getInstance().getExChangeWWB(id, dollId, number, userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG,"兑换结果="+loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")) {
                    UserUtils.UserBalance=loginInfoResult.getData().getAppUser().getBALANCE();
                    mydollStateTv.setText("已兑换");
                    nonesendLayout.setVisibility(View.GONE);
                    exchangedTv.setVisibility(View.VISIBLE);
                    MyToast.getToast(getApplicationContext(), "兑换成功!").show();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


}
