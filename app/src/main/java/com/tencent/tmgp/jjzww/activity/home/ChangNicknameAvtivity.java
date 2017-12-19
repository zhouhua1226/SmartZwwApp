package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.AppUserBean;
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
 * Created by hongxiu on 2017/9/26.
 */
public class ChangNicknameAvtivity extends BaseActivity {
    private static final String TAG = "ChangNicknameAvtivity-";
    @BindView(R.id.back_image_bt)
    ImageButton backImageBt;
    @BindView(R.id.nickname_et)
    EditText nicknameEt;
    @BindView(R.id.changen_image)
    ImageView changenImage;
    @BindView(R.id.save_bt)
    Button saveBt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changenickname;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        if (!UserUtils.NickName.equals("")) {
            nicknameEt.setText(UserUtils.NickName);
        } else {
            nicknameEt.setText("暂无昵称");
        }

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_image_bt, R.id.save_bt, R.id.changen_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image_bt:
                finish();
                break;
            case R.id.save_bt:
                String name = nicknameEt.getText().toString();
                if (Utils.isSpecialChar(name)) {
                    MyToast.getToast(getApplicationContext(), "你输入的包含非法字符，请重新输入！").show();
                } else {
                    getUserName(UserUtils.USER_ID, name);
                }
                break;
            case R.id.changen_image:
                nicknameEt.setText("");
                break;
            default:
                break;
        }
    }

    public void getUserName(String userId, String nickName) {
        //String phones = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
        //Log.e("修改昵称<<<", "手机号加密后=" + phones);
        HttpManager.getInstance().getUserName(userId, nickName, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> result) {
                UserUtils.NickName = result.getData().getAppUser().getNICKNAME();
                MyToast.getToast(ChangNicknameAvtivity.this, "修改成功！").show();
                nicknameEt.setText("");
                finish();
            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "getUserName#####" + e.getMessage());
            }
        });
    }
}
