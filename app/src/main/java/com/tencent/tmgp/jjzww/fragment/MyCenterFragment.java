package com.tencent.tmgp.jjzww.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.home.InformationActivity;
import com.tencent.tmgp.jjzww.activity.home.MyCtachRecordActivity;
import com.tencent.tmgp.jjzww.activity.home.RecordActivity;
import com.tencent.tmgp.jjzww.activity.home.RecordGameActivty;
import com.tencent.tmgp.jjzww.activity.home.RecordGameTwoActivity;
import com.tencent.tmgp.jjzww.activity.home.SelectRechargeTypeActiivty;
import com.tencent.tmgp.jjzww.activity.home.ServiceActivity;
import com.tencent.tmgp.jjzww.activity.home.SettingActivity;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.adapter.MyCenterAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.FillingCurrencyDialog;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;

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

    private FillingCurrencyDialog fillingCurrencyDialog;
    private MyCenterAdapter myCenterAdapter;
    private Unbinder unbinder1;
    private List<String> list;
    private List<VideoBackBean> videoList = new ArrayList<>();
    private List<VideoBackBean> videoReList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Glide.get(getContext()).clearMemory();

    }

    private void onClick() {
        myCenterAdapter.setOnItemClickListener(new MyCenterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), RecordGameActivty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", videoList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                startActivity(new Intent(getContext(), RecordGameTwoActivity.class));
            }
        });

////        下拉刷新
//        swiperefresh.setColorSchemeResources(R.color.pink);
//        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swiperefresh.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getUserImageAndName();
////                        initlist();
//                        myCenterAdapter.notifyDataSetChanged();
//                        swiperefresh.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });

    }

    private void initData() {
        myCenterAdapter = new MyCenterAdapter(getContext(), videoList);
        mycenterRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mycenterRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        mycenterRecyclerview.setAdapter(myCenterAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("<<<<<<<<<<<<","个人中心userId="+UserUtils.USER_ID);
        if(YsdkUtils.loginResult!=null)
        getUserDate(YsdkUtils.loginResult.getData().getAppUser().getUSER_ID());
    }

    private void getUserImageAndName() {
        if (!Utils.isEmpty(UserUtils.USER_ID)) {
            if (!UserUtils.NickName.equals("")) {
                userName.setText(UserUtils.NickName);
            } else {
                userName.setText("暂无昵称");
            }
            mycenterMycurrencyTv.setText(" "+UserUtils.UserBalance);
            userNumber.setText("累积抓中" + UserUtils.UserCatchNum + "次");
            Glide.with(getContext())
                    .load(UserUtils.UserImage)
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
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                UserUtils.UserCatchNum=result.getData().getAppUser().getDOLLTOTAL();
                UserUtils.NickName=result.getData().getAppUser().getNICKNAME();
                UserUtils.UserImage = UrlUtils.USERFACEIMAGEURL + result.getData().getAppUser().getIMAGE_URL();
                Log.e(TAG, "个人信息刷新结果=" + result.getMsg()+"余额="+result.getData().getAppUser().getBALANCE()
                        +"抓取次数="+result.getData().getAppUser().getDOLLTOTAL()
                        +"昵称="+result.getData().getAppUser().getNICKNAME()
                        +"头像="+UserUtils.UserImage);
                getUserImageAndName();
            }

            @Override
            public void _onError(Throwable e) {
            }
        });
    }

    @OnClick({R.id.mycenter_kefu_layout, R.id.mycenter_setting_layout, R.id.user_image,
            R.id.mycenter_pay_layout, R.id.user_name,R.id.mycenter_catchrecord_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mycenter_kefu_layout:
                startActivity(new Intent(getContext(),ServiceActivity.class));
                break;
            case R.id.mycenter_setting_layout:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.user_image:
                startActivity(new Intent(getContext(), InformationActivity.class));
                break;
            case R.id.mycenter_pay_layout:
//                startActivity(new Intent(getContext(), SelectRechargeTypeActiivty.class));
//                getMoney();
                startActivity(new Intent(getContext(),WeChatPayActivity.class));
                break;
            case R.id.mycenter_catchrecord_layout:
                startActivity(new Intent(getContext(), MyCtachRecordActivity.class));
                break;
            case R.id.user_name:
                //此处添加登录dialog
                break;
            default:
                break;
        }
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(getContext(), R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick = new FillingCurrencyDialog.MyDialogClick() {
        @Override
        public void getMoney10(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney20(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney50(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }

        @Override
        public void getMoney100(String money) {
            Intent intent = new Intent(getContext(), WeChatPayActivity.class);
            intent.putExtra("money", money);
            getContext().startActivity(intent);
        }
    };

//    private void getVideoBackList(String userName) {
//        HttpManager.getInstance().getVideoBackList(userName, new RequestSubscriber<Result<LoginInfo>>() {
//            @Override
//            public void _onSuccess(Result<LoginInfo> result) {
//                videoList = result.getData().getPlayback();
//                videoReList = result.getData().getDollCount();
//                Utils.showLogE("mycenter记录列表", "result=" + result.getMsg() + "=" + videoList.size());
//                if (videoList.size() != 0) {
//                    userNumber.setText("累积抓中" + videoList.get(0).getDOLLTOTAL() + "次");
//                    mycenterNoneTv.setVisibility(View.GONE);
//                    //myCenterAdapter.notify(getCatchNum(removeDuplicate(videoList),videoReList));
//                    myCenterAdapter.notify(videoList);
//                } else {
//                    Utils.showLogE("个人中心", "暂无数据");
//                    mycenterRecyclerview.setVisibility(View.GONE);
//                    mycenterNoneTv.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void _onError(Throwable e) {
//
//            }
//        });
//    }
//
//    //记录数据重组   11/28 17:55
//    private List<VideoBackBean> getCatchNum(List<VideoBackBean> list, List<VideoBackBean> reList) {
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getDOLLNAME().equals(reList.get(i).getDOLLNAME())) {
//                list.get(i).setCOUNT(reList.get(i).getCOUNT());
//            } else {
//                for (int j = 0; j < reList.size(); j++) {
//                    if (reList.get(j).getDOLLNAME().equals(list.get(i).getDOLLNAME())) {
//                        list.get(i).setCOUNT(reList.get(j).getCOUNT());
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//    //记录重复赛选
//    public List<VideoBackBean> removeDuplicate(List<VideoBackBean> list) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            for (int j = list.size() - 1; j > i; j--) {
//                if (list.get(j).getDOLLNAME().equals(list.get(i).getDOLLNAME())) {
//                    list.remove(j);
//                }
//            }
//        }
//
//        return list;
//    }

}
