package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class ConsignmentActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.consignment_rl)
    RelativeLayout consignmentRl;
    @BindView(R.id.shipping_button)
    Button shippingButton;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.times_tv)
    TextView timesTv;
    @BindView(R.id.information_tv)
    TextView informationTv;
    @BindView(R.id.text_tv)
    TextView textTv;
    @BindView(R.id.remark_et)
    EditText remarkEt;

    private String ATG="ConsignmentActivity--";
    private VideoBackBean videoBackBean;
    private List<VideoBackBean> list=new ArrayList<>();
    private String information="";
    private String consignee="尹聪,13687632490,上海市虹口区欧阳路196号10楼612,";

    @Override
    protected int getLayoutId() {
        //申请发货页面
        return R.layout.activity_consignment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        videoBackBean=(VideoBackBean) getIntent().getExtras().getSerializable("sqfh");
        nameTv.setText(videoBackBean.getDOLL_NAME());
        timesTv.setText(Utils.getTime(videoBackBean.getCAMERA_DATE()));
        if(!Utils.isEmpty(UserUtils.UserAddress)) {
            informationTv.setText(UserUtils.UserAddress);
        }else {
            informationTv.setText("新增收货地址");
        }
        Glide.with(this)
                .load(UrlUtils.PICTUREURL+videoBackBean.getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(this))
                .into(titleImg);

    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
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

    @OnClick({R.id.image_back, R.id.consignment_rl, R.id.shipping_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.consignment_rl:
                //新增地址
                startActivity(new Intent(this, NewAddressActivity.class));

                break;
            case R.id.shipping_button:
                information=UserUtils.UserAddress.replace(" ",",");
                String remark=remarkEt.getText().toString();
                String dollId= String.valueOf(videoBackBean.getID());
                if(Utils.isEmpty(information)){
                    MyToast.getToast(this, "请设置收货信息！").show();
                }else {
                    getSendGoods(dollId,"1",information,remark,UserUtils.USER_ID);
                    //finish();
                }
                break;
        }
    }

    private void getSendGoods(String dollID,String number,String consignee,String remark,String userID){
        HttpManager.getInstance().getSendGoods(dollID, number, consignee, remark, userID, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(ATG,"发货结果="+loginInfoResult.getMsg());
                videoBackBean=loginInfoResult.getData().getPlayBack();
                MyToast.getToast(getApplicationContext(), "发货成功，请耐心等待！").show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", videoBackBean);
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
