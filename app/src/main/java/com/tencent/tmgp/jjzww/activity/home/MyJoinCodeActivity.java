package com.tencent.tmgp.jjzww.activity.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.PromomoteBean;
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
 * Created by yincong on 2018/3/16 10:50
 * 修改人：
 * 修改时间：
 * 类描述：加盟码
 */
public class MyJoinCodeActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.myjoincode_code_tv)
    TextView myjoincodeCodeTv;
    @BindView(R.id.myjoincode_copy_btn)
    Button myjoincodeCopyBtn;
    @BindView(R.id.exchangecenter_code_et)
    EditText exchangecenterCodeEt;
    @BindView(R.id.myjoincode_submit_btn)
    Button myjoincodeSubmitBtn;
    @BindView(R.id.myjoincode_mycode_layout)
    RelativeLayout myjoincodeMycodeLayout;
    @BindView(R.id.myjoincode_gotojoin_btn)
    Button myjoincodeGotojoinBtn;

    private String TAG = "MyJoinCodeActivity--";
    private String mycode = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myjoincode;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();

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

    @Override
    protected void onResume() {
        super.onResume();
        getUserPromoteInf(UserUtils.USER_ID);
    }

    @OnClick({R.id.image_back, R.id.myjoincode_copy_btn,R.id.myjoincode_gotojoin_btn,R.id.myjoincode_submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.myjoincode_copy_btn:
                if (!Utils.isEmpty(mycode)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(mycode);
                    MyToast.getToast(getApplicationContext(), "已复制！").show();
                }
                break;
            case R.id.myjoincode_gotojoin_btn:
                startActivity(new Intent(this,JoinEarnActivity.class));
                break;
            case R.id.myjoincode_submit_btn:
                String code=exchangecenterCodeEt.getText().toString().trim();
                if(Utils.isEmpty(code)){
                    MyToast.getToast(getApplicationContext(),"请输入!").show();
                }else {
                    getCommitUserPromoteCode(UserUtils.USER_ID,code);
                }
                break;
            default:
                break;
        }
    }

    private void getUserPromoteInf(String userId) {
        if (Utils.isEmpty(userId)) {
            return;
        }
        HttpManager.getInstance().getUserPromoteInf(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if (httpDataInfoResult.getMsg().equals(Utils.HTTP_OK)) {
                    String flag = httpDataInfoResult.getData().getPromoteFlag();
                    if (flag.equals("1")) {
                        myjoincodeMycodeLayout.setVisibility(View.VISIBLE);
                        myjoincodeGotojoinBtn.setVisibility(View.GONE);
                        PromomoteBean promomoteBean = httpDataInfoResult.getData().getProPd();
                        if (promomoteBean != null) {
                            mycode = promomoteBean.getPRO_ID() + "";
                            myjoincodeCodeTv.setText(mycode);
                        }
                    }else {
                        myjoincodeMycodeLayout.setVisibility(View.GONE);
                        myjoincodeGotojoinBtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(),"网络异常！").show();
            }
        });
    }

    private void getCommitUserPromoteCode(String userId,String promoteCode){
        HttpManager.getInstance().getCommitUserPromoteCode(userId, promoteCode, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if(httpDataInfoResult.getMsg().equals(Utils.HTTP_OK)){
                    exchangecenterCodeEt.setText("");
                    finish();
                    MyToast.getToast(getApplicationContext(),"兑换成功！").show();
                }else {
                    MyToast.getToast(getApplicationContext(),httpDataInfoResult.getMsg()).show();
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(),"网络异常！").show();
            }
        });
    }


}
