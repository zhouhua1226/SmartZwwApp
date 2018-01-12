package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.ConsigneeBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/23.
 */
public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.preserve_button)
    Button preserveButton;
    @BindView(R.id.newaddress_name_et)
    EditText newaddressNameEt;
    @BindView(R.id.newaddress_phone_et)
    EditText newaddressPhoneEt;
    @BindView(R.id.newaddress_dq_et)
    EditText newaddressDqEt;
    @BindView(R.id.newaddress_detail_et)
    EditText newaddressDetailEt;

    private String TAG="NewAddressActivity--";
    private String name="";
    private String phone="";
    private String address="";
    private String information="";

    @Override
    protected int getLayoutId() {
        //新增地址页面
        return R.layout.activity_newaddress;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initAddress();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initAddress(){
        if(!UserUtils.UserAddress.equals("")){
           String[] sh=UserUtils.UserAddress.split(" ");
            int lenght=sh.length;
            if (lenght>0)
            newaddressNameEt.setText(sh[0]);
            if (lenght>1)
            newaddressPhoneEt.setText(sh[1]);
            if (lenght>2)
            newaddressDetailEt.setText(sh[2]);
        }
    }

    private void initData(){
        name=newaddressNameEt.getText().toString();
        phone=newaddressPhoneEt.getText().toString();
        address=newaddressDetailEt.getText().toString();
    }

    @OnClick({R.id.image_back, R.id.preserve_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.preserve_button:
                initData();
                if(Utils.isEmpty(name)||Utils.isEmpty(phone)||Utils.isEmpty(address)){
                    MyToast.getToast(this, "请将信息填写完整！").show();

                }else {
                    if(Utils.isSpecialChar(name)||Utils.isSpecialChar(phone)||Utils.isSpecialChar(address)){
                        MyToast.getToast(this, "您输入了特殊字符！").show();
                    }else {
                        information=name+"  "+phone+"  "+address;
                        UserUtils.UserAddress=information;
                        getConsignee(name,phone,address,UserUtils.USER_ID);
                    }

                }


                break;
        }
    }

    private void getConsignee(String name,String phone,String address,String userID){
        HttpManager.getInstance().getConsignee(name, phone, address, userID, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG,"收货信息结果="+loginInfoResult.getMsg());
                String name=loginInfoResult.getData().getAppUser().getCNEE_NAME();
                String phone=loginInfoResult.getData().getAppUser().getCNEE_PHONE();
                String address=loginInfoResult.getData().getAppUser().getCNEE_ADDRESS();
                UserUtils.UserAddress=name+" "+phone+" "+address;
                MyToast.getToast(getApplicationContext(), "保存成功！").show();
                finish();
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


}
