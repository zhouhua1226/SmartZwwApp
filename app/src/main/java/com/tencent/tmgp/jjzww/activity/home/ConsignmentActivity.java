package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.ConsignmentAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.MyToast;

import java.io.Serializable;
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
    @BindView(R.id.consignment_recyclerview)
    RecyclerView consignmentRecyclerview;
    @BindView(R.id.consignment_hdfk_tv)
    TextView consignmentHdfkTv;
    @BindView(R.id.consignment_wwbdkyf_tv)
    TextView consignmentWwbdkyfTv;
    @BindView(R.id.consignment_singleyj_layout)
    LinearLayout consignmentSingleyjLayout;
    @BindView(R.id.consignment_hdfk_imag)
    ImageView consignmentHdfkImag;
    @BindView(R.id.consignment_wwbdkyf_imag)
    ImageView consignmentWwbdkyfImag;
    @BindView(R.id.consignment_hdfk_layout)
    LinearLayout consignmentHdfkLayout;
    @BindView(R.id.consignment_wwbdkyf_layout)
    LinearLayout consignmentWwbdkyfLayout;

    private String TAG = "ConsignmentActivity--";
    private VideoBackBean videoBackBean;
    private ConsignmentAdapter consignmentAdapter;
    private List<VideoBackBean> list = new ArrayList<>();
    private String information = "";
    private String fhType = "0";
    private StringBuffer stringId = new StringBuffer("");
    private StringBuffer stringDollId = new StringBuffer("");
    private String consignee = "尹聪,13687632490,上海市虹口区欧阳路196号10楼612,";

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

    private void initData() {
        if (!Utils.isEmpty(UserUtils.UserAddress)) {
            informationTv.setText(UserUtils.UserAddress);
            consignmentWwbdkyfTv.setText(Utils.getJBDKNum(UserUtils.UserAddress)+"娃娃币抵扣邮费");
        } else {
            informationTv.setText("新增收货地址");
            consignmentWwbdkyfTv.setText("娃娃币抵扣邮费");
        }
        list = (List<VideoBackBean>) getIntent().getSerializableExtra("record");//获取list方式
        Log.e(TAG, "发货娃娃集合长度=" + list.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        consignmentRecyclerview.setLayoutManager(linearLayoutManager);
        consignmentRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        consignmentAdapter = new ConsignmentAdapter(this, list);
        consignmentRecyclerview.setAdapter(consignmentAdapter);

        if (list.size() >= 2) {
            consignmentSingleyjLayout.setVisibility(View.GONE);
        } else {
            consignmentSingleyjLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        //initData();

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

    @OnClick({R.id.image_back, R.id.consignment_rl, R.id.shipping_button,
            R.id.consignment_hdfk_tv, R.id.consignment_wwbdkyf_tv,
            R.id.consignment_hdfk_layout,R.id.consignment_wwbdkyf_layout})
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
                if(!Utils.isEmpty(UserUtils.UserAddress)){
                    information = UserUtils.UserAddress.replace(" ", ",");
                }
                String remark = remarkEt.getText().toString();
                if(Utils.isSpecialChar(remark)){
                    MyToast.getToast(this, "您输入了特殊字符！").show();
                    return;
                }
                final int length = list.size();
                if (length > 1) {
                    for (int i = 0; i < length; i++) {
                        if (i == 0) {
                            Log.e(TAG, "adsadadfad" + (list.get(i).getID()));
                            stringId.append(list.get(i).getID());
                            stringDollId.append(list.get(i).getDOLLID());
                        } else {
                            stringId.append("," + list.get(i).getID());
                            stringDollId.append(list.get(i).getDOLLID());
                        }
                    }
                    Log.e(TAG, "发货娃娃编号=" + stringId);
                    if (Utils.isEmpty(information)) {
                        MyToast.getToast(this, "请设置收货信息！").show();
                    } else {
                        getSendGoods(String.valueOf(stringId), length + "", information, remark, UserUtils.USER_ID, "0", Utils.getProvinceNum(UserUtils.UserAddress));
                    }
                } else {
                    if (Utils.isEmpty(information)) {
                            MyToast.getToast(this, "请设置收货信息！").show();
                        } else {
                            if (fhType.equals("1") || fhType.equals("2")) {
                                Log.e(TAG, "单个娃娃id" + list.get(0).getID()+fhType);
                                if(isEnough()) {
                                    //getSendGoods(list.get(0).getID()+",", "1", information, remark, UserUtils.USER_ID, fhType);
                                    getSendGoods(list.get(0).getID() + ",", "1", information, remark, UserUtils.USER_ID, fhType, Utils.getProvinceNum(UserUtils.UserAddress));
                                }else {
                                    MyToast.getToast(getApplicationContext(),"您的余额不足！").show();
                                }
                            }else {
                                MyToast.getToast(this,"请选择邮寄付款方式！").show();
                            }
                        }

                }
                break;
            case R.id.consignment_hdfk_layout:
                //选货到付款  免邮：0  金币抵扣 ：1  货到付款 ：2
                fhType = "2";
                setYJType(fhType);
                break;
            case R.id.consignment_wwbdkyf_layout:
                //娃娃币抵扣运费
                fhType = "1";
                setYJType(fhType);
                break;
        }
    }

    private void setYJType(String type) {
        if (type.equals("2")) {
            consignmentHdfkImag.setImageResource(R.drawable.consignment_select);
            consignmentWwbdkyfImag.setImageResource(R.drawable.consignment_unselect);
        } else if (type.equals("1")) {
            consignmentHdfkImag.setImageResource(R.drawable.consignment_unselect);
            consignmentWwbdkyfImag.setImageResource(R.drawable.consignment_select);
        }
    }

    private void getSendGoods(String dollID, String number, String consignee, String remark, String userID, String mode,String costNum) {
        //Log.e(TAG, "发货参数=" + costNum);
        HttpManager.getInstance().getSendGoods(dollID, number, consignee, remark, userID, mode,costNum, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> loginInfoResult) {
                Log.e(TAG, "发货结果=" + loginInfoResult.getMsg());
                list = loginInfoResult.getData().getPlayback();
                MyToast.getToast(getApplicationContext(), "发货成功，请耐心等待！").show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", (Serializable) list);
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    /**
     * 判断用户余额是否够付邮费
     * @return
     */
    private boolean isEnough(){
        int yf= Integer.parseInt(Utils.getJBDKNum(UserUtils.UserAddress));
        int ye= Integer.parseInt(UserUtils.UserBalance);
        if(ye>=yf){
            return true;
        }else {
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
