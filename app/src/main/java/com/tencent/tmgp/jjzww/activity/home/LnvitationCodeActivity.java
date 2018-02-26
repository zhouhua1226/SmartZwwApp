package com.tencent.tmgp.jjzww.activity.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.AwardCodeBean;
import com.tencent.tmgp.jjzww.bean.AwardCodePdBean;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.MyToast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class LnvitationCodeActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.submit_bt)
    Button submitBt;
    @BindView(R.id.invitationcode_title_tv)
    TextView invitationcodeTitleTv;
    @BindView(R.id.invitationcode_submit_layout)
    RelativeLayout invitationcodeSubmitLayout;
    @BindView(R.id.invitationcode_rule_tv)
    TextView invitationcodeRuleTv;
    @BindView(R.id.invitationcode_hasnum_tv)
    TextView invitationcodeHasnumTv;
    @BindView(R.id.invitationcode_mycode_tv)
    TextView invitationcodeMycodeTv;
    @BindView(R.id.invitationcode_share_btn)
    Button invitationcodeShareBtn;
    @BindView(R.id.invitationcode_copy_btn)
    Button invitationcodeCopyBtn;
    @BindView(R.id.invitationcode_test_tv)
    TextView invitationcodeTestTv;

    private String codeNum = "0";
    private String currencyNum = "0";
    private String isExChange="";
    private String inviteTotalNum="";  //兑换次数上限
    private String inviteAmount="";    //邀请人奖励金额
    private String exchangeAmount="";  //被邀请人奖励金额
    private String TAG = "LnvitationCodeActivity";
    private String string;
    String newsString;

    //邀请码
    @Override
    protected int getLayoutId() {
        return R.layout.activity_lnvitationcode;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        getViewDate();
        getUserAwardCode(UserUtils.USER_ID);
//        invitationcodeTestTv.setText("APP版本=" + Utils.appVersion +
//                "&手机IMEI=" + Utils.IMEI +
//                "&手机型号=" + Utils.deviceType +
//                "&系统版本=" + Utils.osVersion+
//                "&昵称="+ YsdkUtils.nickName+
//                "&是否包含表情="+containsEmoji(YsdkUtils.nickName)+
//                "&转义后昵称="+filterEmoji(YsdkUtils.nickName)+
//                "&解析后昵称="+setString(filterEmoji(YsdkUtils.nickName)));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }


    @OnClick({R.id.image_back, R.id.submit_bt, R.id.invitationcode_share_btn, R.id.invitationcode_copy_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.submit_bt:
                String code=codeEt.getText().toString();
                if(!code.equals("")){
                    doAwardByUserCode(UserUtils.USER_ID,code);
                }else {
                    Toast.makeText(this, "请输入邀请码!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.invitationcode_share_btn:
                //此处加分享逻辑
                break;
            case R.id.invitationcode_copy_btn:
                String yqm = invitationcodeMycodeTv.getText().toString();
                if (!yqm.equals("")) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(yqm);
                    MyToast.getToast(this, "复制成功！").show();
                } else {
                    MyToast.getToast(this, "你的邀请码为空！").show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void getViewDate() {
        //boolean isExChange=(boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_ISEXCHANGE, false);
        if(isExChange.equals("Y")){
            invitationcodeSubmitLayout.setVisibility(View.GONE);
            invitationcodeTitleTv.setText("已输入过邀请码");
        }else {
            invitationcodeSubmitLayout.setVisibility(View.VISIBLE);
            invitationcodeTitleTv.setText("输入邀请码");
        }
        invitationcodeRuleTv.setText("1.每邀请一位好友，用户可获得"+inviteAmount+"金币，好友可获得"+exchangeAmount+"金币\r\n"
                + "2.邀请奖励上限为"+inviteTotalNum+"名\r\n"
                + "3.用户无法兑换自己的邀请码");
        invitationcodeHasnumTv.setText(Html.fromHtml("已邀请<font color='#ff9700'>" + codeNum
                + "</font>" + "人获得<font color='#ff9700'>" + currencyNum + "</font>金币"));

        Log.e(TAG, "APP版本=" + Utils.appVersion +
                "&手机IMEI=" + Utils.IMEI +
                "&手机型号=" + Utils.deviceType +
                "&系统版本=" + Utils.osVersion);



    }

    /**
     * 查询邀请码
     * @param userId
     */
    private void getUserAwardCode(String userId) {
        if(Utils.isEmpty(userId)){
            return;
        }
        HttpManager.getInstance().getUserAwardCode(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if(httpDataInfoResult.getMsg().equals(Utils.HTTP_OK)){
                    HttpDataInfo httpDataInfo=httpDataInfoResult.getData();
                    if(httpDataInfo!=null){
                        invitationcodeMycodeTv.setText(httpDataInfo.getCodeVale());
                        AwardCodeBean awardCodeBean=httpDataInfo.getAwardPd();
                        AwardCodePdBean pdBean=httpDataInfo.getSetPd();
                        isExChange=httpDataInfo.getAwradFlag();
                        if(awardCodeBean!=null){
                            codeNum=awardCodeBean.getAWARDCOUNT()+"";
                            currencyNum=awardCodeBean.getAWARDSUM()+"";
                        }
                        if(pdBean!=null){
                            inviteTotalNum=pdBean.getInviteTotalNum();
                            inviteAmount=pdBean.getInviteAmount();
                            exchangeAmount=pdBean.getExchangeAmount();
                        }
                        getViewDate();
                    }

                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    /**
     * 兑换邀请码
     * @param userId
     * @param awardCode
     */
    private void doAwardByUserCode(String userId,String awardCode){
        HttpManager.getInstance().doAwardByUserCode(userId, awardCode, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if (httpDataInfoResult.getMsg().equals("success")){
                    MyToast.getToast(getApplicationContext(),"兑换成功!").show();
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISEXCHANGE, true);
                    invitationcodeSubmitLayout.setVisibility(View.GONE);
                    invitationcodeTitleTv.setText("已输入过邀请码");
                }else {
                    MyToast.getToast(getApplicationContext(),httpDataInfoResult.getMsg()).show();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


}
