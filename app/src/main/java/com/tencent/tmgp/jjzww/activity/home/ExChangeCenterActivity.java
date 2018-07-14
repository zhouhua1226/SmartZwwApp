package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
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
 * Created by yincong on 2018/3/16 10:47
 * 修改人：
 * 修改时间：
 * 类描述：兑换中心
 */
public class ExChangeCenterActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.exchangecenter_code_et)
    EditText exchangecenterCodeEt;
    @BindView(R.id.myjoincode_submit_btn)
    Button myjoincodeSubmitBtn;
    @BindView(R.id.myjoincode_myjoincode_btn)
    Button myjoincodeMyjoincodeBtn;

    private String TAG="ExChangeCenter--";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchangecenter;
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

    @OnClick({R.id.image_back, R.id.myjoincode_submit_btn, R.id.myjoincode_myjoincode_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.myjoincode_submit_btn:
                String code=exchangecenterCodeEt.getText().toString().trim();
                if(Utils.isEmpty(code)){
                    MyToast.getToast(getApplicationContext(),"请输入!").show();
                }else {
                    getCommitUserPromoteCode(UserUtils.USER_ID,code);
                }
                break;
            case R.id.myjoincode_myjoincode_btn:
                startActivity(new Intent(this,LnvitationCodeActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private void getCommitUserPromoteCode(String userId,String promoteCode){
        HttpManager.getInstance().getCommitUserPromoteCode(userId, promoteCode, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if(httpDataInfoResult.getMsg().equals(Utils.HTTP_OK)){
                    MyToast.getToast(getApplicationContext(),"提交成功！").show();
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
