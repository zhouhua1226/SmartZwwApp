package com.tencent.tmgp.jjzww.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.home.BetRecordActivity;
import com.tencent.tmgp.jjzww.activity.home.GameCurrencyActivity;
import com.tencent.tmgp.jjzww.activity.home.InformationActivity;
import com.tencent.tmgp.jjzww.activity.home.MyCtachRecordActivity;
import com.tencent.tmgp.jjzww.activity.home.MyLogisticsOrderActivity;
import com.tencent.tmgp.jjzww.activity.home.RecordActivity;
import com.tencent.tmgp.jjzww.activity.home.ServiceActivity;
import com.tencent.tmgp.jjzww.activity.home.SettingActivity;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class MyCenterFragment extends BaseFragment {

    private static final String TAG = "MyCenterFragment";
    Unbinder unbinder;
    @BindView(R.id.image_kefu)
    ImageButton imageKefu;
    @BindView(R.id.image_setting)
    ImageButton imageSetting;
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_number)
    TextView userNumber;
    @BindView(R.id.user_filling)
    TextView userFilling;
    @BindView(R.id.mycenter_recyclerview)
    RecyclerView mycenterRecyclerview;
    @BindView(R.id.mycenter_none_tv)
    TextView mycenterNoneTv;
    @BindView(R.id.mycenter_pay_layout)
    RelativeLayout mycenterPayLayout;
    @BindView(R.id.mycenter_catchrecord_layout)
    RelativeLayout mycenterCatchrecordLayout;
    @BindView(R.id.mycenter_setting_layout)
    RelativeLayout mycenterSettingLayout;
    @BindView(R.id.mycenter_kefu_layout)
    RelativeLayout mycenterKefuLayout;
    @BindView(R.id.mycenter_mycurrency_tv)
    TextView mycenterMycurrencyTv;
    @BindView(R.id.mycenter_videoback_layout)
    RelativeLayout mycenterVideobackLayout;
    Unbinder unbinder2;
    @BindView(R.id.mycenter_currencyrecord_layout)
    RelativeLayout mycenterCurrencyrecordLayout;
    @BindView(R.id.mycenter_guessrecord_layout)
    RelativeLayout mycenterGuessrecordLayout;
    @BindView(R.id.mycenter_logisticsorder_layout)
    RelativeLayout mycenterLogisticsorderLayout;

    private List<VideoBackBean> videoList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Glide.get(getContext()).clearMemory();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("mycenter_onResume", "个人中心userId=" + UserUtils.USER_ID);
        if (!Utils.isEmpty(UserUtils.USER_ID))
            getUserDate(UserUtils.USER_ID);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.e("mycenter_onHidden", "个人中心userId=" + UserUtils.USER_ID);
            if (!Utils.isEmpty(UserUtils.USER_ID))
                getUserDate(UserUtils.USER_ID);
        }
    }

    private void getUserImageAndName() {
        if (!Utils.isEmpty(UserUtils.USER_ID)) {
            if (!UserUtils.NickName.equals("")) {
                userName.setText(UserUtils.NickName);
            } else {
                userName.setText("暂无昵称");
            }
            mycenterMycurrencyTv.setText(" " + UserUtils.UserBalance);
            userNumber.setText("累积抓中" + UserUtils.UserCatchNum + "次");
            Glide.with(getContext())
                    .load(UserUtils.UserImage)
                    .error(R.drawable.ctrl_default_user_bg)
                    .dontAnimate()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(userImage);
        } else {
            userName.setText("请登录");
            videoList.clear();
            userImage.setImageResource(R.drawable.round);
        }
    }

    private void getUserDate(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> result) {
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                UserUtils.UserCatchNum = result.getData().getAppUser().getDOLLTOTAL();
                UserUtils.NickName = result.getData().getAppUser().getNICKNAME();
                UserUtils.UserImage = UrlUtils.APPPICTERURL + result.getData().getAppUser().getIMAGE_URL();
                String name = result.getData().getAppUser().getCNEE_NAME();
                String phone = result.getData().getAppUser().getCNEE_PHONE();
                String address = result.getData().getAppUser().getCNEE_ADDRESS();
                UserUtils.UserAddress = name + " " + phone + " " + address;
                Log.e(TAG, "个人信息刷新结果=" + result.getMsg() + "余额=" + result.getData().getAppUser().getBALANCE()
                        + " 抓取次数=" + result.getData().getAppUser().getDOLLTOTAL()
                        + " 昵称=" + result.getData().getAppUser().getNICKNAME()
                        + " 头像=" + UserUtils.UserImage
                        + " 发货地址=" + UserUtils.UserAddress);
                getUserImageAndName();
            }

            @Override
            public void _onError(Throwable e) {
            }
        });
    }

    @OnClick({R.id.mycenter_kefu_layout, R.id.mycenter_setting_layout, R.id.user_image,
            R.id.mycenter_pay_layout, R.id.user_name, R.id.mycenter_catchrecord_layout,
            R.id.mycenter_videoback_layout, R.id.mycenter_currencyrecord_layout,
            R.id.mycenter_guessrecord_layout,R.id.mycenter_logisticsorder_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mycenter_kefu_layout:
                startActivity(new Intent(getContext(), ServiceActivity.class));
                break;
            case R.id.mycenter_setting_layout:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.user_image:
                startActivity(new Intent(getContext(), InformationActivity.class));
                break;
            case R.id.mycenter_pay_layout:
//                startActivity(new Intent(getContext(), SelectRechargeTypeActiivty.class));
                startActivity(new Intent(getContext(), WeChatPayActivity.class));
                break;
            case R.id.mycenter_catchrecord_layout:
                startActivity(new Intent(getContext(), MyCtachRecordActivity.class));
                break;
            case R.id.mycenter_videoback_layout:
                startActivity(new Intent(getContext(), RecordActivity.class));
                break;
            case R.id.user_name:
                //此处添加登录dialog
                break;
            case R.id.mycenter_currencyrecord_layout:
                //金币记录
                //MyToast.getToast(getContext(),"金币记录").show();
                startActivity(new Intent(getContext(), GameCurrencyActivity.class));
                break;
            case R.id.mycenter_guessrecord_layout:
                startActivity(new Intent(getContext(), BetRecordActivity.class));
                break;
            case R.id.mycenter_logisticsorder_layout:
                startActivity(new Intent(getContext(), MyLogisticsOrderActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder2.unbind();
    }


}
