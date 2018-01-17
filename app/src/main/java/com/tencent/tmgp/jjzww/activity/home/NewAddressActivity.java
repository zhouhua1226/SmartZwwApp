package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.ConsigneeBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.MyBankSpinner;
import com.tencent.tmgp.jjzww.view.MyToast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.newaddress_dq_tv)
    public TextView newaddressDqTv;
    @BindView(R.id.newaddress_detail_et)
    EditText newaddressDetailEt;

    private String TAG="NewAddressActivity--";
    private List<Map<String, String>> listProvince = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> listCity = new ArrayList<Map<String, String>>();
    private MyBankSpinner spinner_province, spinner_city;
    private String  bankInProvinceId;
    private String name="";
    private String phone="";
    private String address="";
    private String information="";
    public int province_index = -1;
    public int city_index = -1;

    @Override
    protected int getLayoutId() {
        //新增地址页面
        return R.layout.activity_newaddress;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initAddress();
        getData(listProvince, "provincename", R.xml.province);
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

    @OnClick({R.id.image_back, R.id.preserve_button,R.id.newaddress_dq_tv})
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
                        //UserUtils.UserAddress=information;
                        getConsignee(name,phone,address,UserUtils.USER_ID);
                    }

                }
                break;
            case R.id.newaddress_dq_tv:
                spinner_province = new MyBankSpinner(this,
                        listProvince, province_index, Utils.PROVINCE_TYPE,
                        R.style.dialog);
                spinner_province.show();
                break;
            default:
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

    /**
     * 解析银行和省的XML
     */
    private void getData(List<Map<String, String>> list, String name2, int xml) {
        // 先清除
        list.clear();
        XmlResourceParser xrp = getResources().getXml(xml);
        try {
            Map<String, String> map = null;
            // 直到文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();// 获取标签的名字
                    if (tagName.equals("row")) {
                        map = new HashMap<String, String>();
                        String id = xrp.getAttributeValue(null, "id");// 通过属性名来获取属性值
                        String nm = xrp.getAttributeValue(null, name2);// 通过属性名来获取属性值
                        map.put("id", id);
                        map.put("name", nm);
                        list.add(map);
                    }
                }
                xrp.next();// 获取解析下一个事件
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析城市的XML
     */
    private void getData2(String bankInProvinceId) {
        listCity.clear();
        XmlResourceParser xrp = getResources().getXml(R.xml.city);
        try {
            // 直到文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();// 获取标签的名字
                    if (tagName.equals("row")) {
                        Map<String, String> map = new HashMap<String, String>();
                        String proId = xrp
                                .getAttributeValue(null, "provinceid");// 通过属性名来获取属性值
                        if (bankInProvinceId.equals(proId)) {
                            String id = xrp.getAttributeValue(null, "id");// 通过属性名来获取属性值
                            String cityname = xrp.getAttributeValue(null,
                                    "cityname");// 通过属性名来获取属性值
                            map.put("id", id);
                            map.put("name", cityname);
                            listCity.add(map);
                        }
                    }
                }
                xrp.next();// 获取解析下一个事件
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean changCity() {
        // 判断是否选择了省份
        if (null!=spinner_city  && spinner_city.isShowing()) {
            spinner_city.dismiss();
        }
        if (province_index != -1) {
            for (Object o : listProvince.get(province_index).entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                if ("id".equals(key)) {
                    bankInProvinceId = (String) entry.getValue();
                    getData2(bankInProvinceId);
                }
            }
            spinner_city = new MyBankSpinner(this, listCity,
                    city_index, Utils.CITY_TYPE, R.style.dialog);
            spinner_city.show();
            return true;
        } else {
            return false;
        }
    }


}
