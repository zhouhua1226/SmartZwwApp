package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;

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

    private String codeNum="0";
    private String currencyNum="0";

    //邀请码
    @Override
    protected int getLayoutId() {
        return R.layout.activity_lnvitationcode;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        getViewDate();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }


    @OnClick({R.id.image_back, R.id.submit_bt,R.id.invitationcode_share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.submit_bt:
                Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
                break;
            case R.id.invitationcode_share_btn:
                //此处加分享逻辑
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

    private void getViewDate(){
        invitationcodeRuleTv.setText("1.每邀请一位好友，双方均可获得30金币\r\n"
                                        +"2.邀请奖励上限为10名，最多可获300金币奖励\r\n"
                                        +"3.邀请人数无上限");
        ;
        invitationcodeHasnumTv.setText(Html.fromHtml("已邀请<font color='#ff9700'>"+codeNum+"</font>"+"人获得<font color='#ff9700'>"+currencyNum+"</font>金币"));

    }




}
