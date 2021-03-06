package com.tencent.tmgp.jjzww.activity.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gatz.netty.AppClient;
import com.gatz.netty.utils.AppProperties;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.base.MyApplication;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.bean.RoomListBean;
import com.tencent.tmgp.jjzww.fragment.MyCenterFragment;
import com.tencent.tmgp.jjzww.fragment.RankFragmentTwo;
import com.tencent.tmgp.jjzww.fragment.ZWWJFragment;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.SignInDialog;
import com.tencent.tmgp.jjzww.view.SignSuccessDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity-";

    @BindView(R.id.iv_tab_zww)
    ImageView ivTabZww;//娃娃机图标
    @BindView(R.id.layout_tab_zww)
    RelativeLayout layoutTabZww;//娃娃机图标布局
    @BindView(R.id.iv_tab_list)
    ImageView ivTabList;//排行榜图标
    @BindView(R.id.layout_tab_list)
    LinearLayout layoutTabList;//排行旁图标布局
    @BindView(R.id.iv_tab_my)
    ImageView ivTabMy;//我的图标
    @BindView(R.id.layout_tab_my)
    LinearLayout layoutTabMy;//我的图标布局

    private MyCenterFragment myCenterFragment;//个人中心
    private RankFragmentTwo rankFragment;//排行榜
    private ZWWJFragment zwwjFragment;//抓娃娃
    private Fragment fragmentAll;
    private long mExitTime;
    private List<RoomBean> roomList=new ArrayList<>();
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Result<HttpDataInfo> loginInfoResult;
    private int signNumber = 0;
    private int[] signDayNum=new int[7];
    private String isSign="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    static {
        System.loadLibrary("SmartPlayer");
    }
    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        showZwwFg();
        initNetty();
        getDollList();                  //获取房间列表
        RxBus.get().register(this);
        initData();
    }

    private void initData() {
        settings = getSharedPreferences("app_user", 0);// 获取SharedPreference对象
        editor = settings.edit();// 获取编辑对象。
        editor.putBoolean("isVibrator",true);
        editor.putBoolean("isOpenMusic",true);
        editor.commit();
        UserUtils.isUserChanger = false;
        getUserSign(UserUtils.USER_ID,"0"); //签到请求 0 查询签到信息 1签到

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        fragmentAll = getSupportFragmentManager().findFragmentById(
                R.id.main_center);
    }

    private void initNetty() {
        doServcerConnect();
        NettyUtils.registerAppManager();
    }

    private void initDoConnect() {
        if ((YsdkUtils.loginResult != null) && (zwwjFragment != null)){
            UserUtils.NickName = YsdkUtils.loginResult.getData().getAppUser().getNICKNAME();
            UserUtils.USER_ID = YsdkUtils.loginResult.getData().getAppUser().getUSER_ID();
            zwwjFragment.setSessionId(YsdkUtils.loginResult.getData().getSessionID(), false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoginBackDate();             //登录信息返回

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.isExit = true;
        //stopTimer();
        RxBus.get().unregister(this);
    }

    private void getLoginBackDate(){
        loginInfoResult=YsdkUtils.loginResult;
        if(loginInfoResult!=null&&!loginInfoResult.equals("")){
            if (loginInfoResult.getMsg().equals(Utils.HTTP_OK)) {
                Utils.showLogE(TAG, "logIn::::" + loginInfoResult.getMsg());
                Utils.token = loginInfoResult.getData().getAccessToken();
                //dollLists = loginInfoResult.getData().getDollList();
                UserUtils.SRSToken=loginInfoResult.getData().getSRStoken();
                //用户手机号
                UserUtils.UserPhone = loginInfoResult.getData().getAppUser().getPHONE();
                //用户名  11/22 13：25
                UserUtils.UserName = loginInfoResult.getData().getAppUser().getUSERNAME();
                UserUtils.NickName = loginInfoResult.getData().getAppUser().getNICKNAME();
                //用户余额
                UserUtils.UserBalance = loginInfoResult.getData().getAppUser().getBALANCE();
                //用户头像  11/22 13：25
                UserUtils.UserImage = UrlUtils.APPPICTERURL + loginInfoResult.getData().getAppUser().getIMAGE_URL();
                UserUtils.UserCatchNum = loginInfoResult.getData().getAppUser().getDOLLTOTAL();
                UserUtils.DOLL_ID = loginInfoResult.getData().getAppUser().getDOLL_ID();
                UserUtils.USER_ID = loginInfoResult.getData().getAppUser().getUSER_ID();
                UserUtils.UserAddress = loginInfoResult.getData().getAppUser().getCNEE_NAME() + " " +
                        loginInfoResult.getData().getAppUser().getCNEE_PHONE() + " " +
                        loginInfoResult.getData().getAppUser().getCNEE_ADDRESS();
            }
        }else {
            if (zwwjFragment != null) {
                zwwjFragment.showError();
            }
        }
    }

    /**
     * 设置未选中状态
     */
    private void setFocuse() {
        ivTabZww.setBackgroundResource(R.drawable.zww_unicon_jj);
        ivTabList.setBackgroundResource(R.drawable.rank_unicon_jj);
        ivTabMy.setBackgroundResource(R.drawable.mycenter_unicon_jj);
    }

    private void showZwwFg() {
        if (!(fragmentAll instanceof ZWWJFragment)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            //如果所有的fragment都不为空的话，把所有的fragment都进行隐藏。最开始进入应用程序，fragment为空时，此方法不执行
            hideFragment(fragmentTransaction);
            //如果这个fragment为空的话，就创建一个fragment，并且把它加到ft中去.如果不为空，就把它直接给显示出来
            if(zwwjFragment == null){
                zwwjFragment = new ZWWJFragment();
                fragmentTransaction.add(R.id.main_center, zwwjFragment);
            }else {
                fragmentTransaction.show(zwwjFragment);
            }
            setFocuse();
            ivTabZww.setBackgroundResource(R.drawable.zww_icon_jj);
            //一定要记得提交
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void showRankFg() {
        if (!(fragmentAll instanceof RankFragmentTwo)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            //如果所有的fragment都不为空的话，把所有的fragment都进行隐藏。最开始进入应用程序，fragment为空时，此方法不执行
            hideFragment(fragmentTransaction);
            //如果这个fragment为空的话，就创建一个fragment，并且把它加到ft中去.如果不为空，就把它直接给显示出来
            if(rankFragment==null) {
                rankFragment = new RankFragmentTwo();
                fragmentTransaction.add(R.id.main_center, rankFragment);
            }else {
                fragmentTransaction.show(rankFragment);
            }
            setFocuse();
            ivTabList.setBackgroundResource(R.drawable.rank_icon_jj);
            //一定要记得提交
            fragmentTransaction.commitAllowingStateLoss();
        }

    }

    private void showMyCenterFg() {
        if (!(fragmentAll instanceof MyCenterFragment)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            //如果所有的fragment都不为空的话，把所有的fragment都进行隐藏。最开始进入应用程序，fragment为空时，此方法不执行
            hideFragment(fragmentTransaction);
            //如果这个fragment为空的话，就创建一个fragment，并且把它加到ft中去.如果不为空，就把它直接给显示出来
            if(myCenterFragment == null){
                myCenterFragment = new MyCenterFragment();
                fragmentTransaction.add(R.id.main_center,myCenterFragment);
            }else {
                fragmentTransaction.show(myCenterFragment);
            }
            setFocuse();
            ivTabMy.setBackgroundResource(R.drawable.mycenter_icon_jj);
            //一定要记得提交
            fragmentTransaction.commitAllowingStateLoss();
        }

    }

    //隐藏fragment
    public void hideFragment(FragmentTransaction fragmentTransaction){
        if(zwwjFragment != null){
            fragmentTransaction.hide(zwwjFragment);
        }
        if(rankFragment != null){
            fragmentTransaction.hide(rankFragment);
        }
        if(myCenterFragment != null){
            fragmentTransaction.hide(myCenterFragment);
        }
    }

    @OnClick({R.id.layout_tab_zww, R.id.layout_tab_list, R.id.layout_tab_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //抓娃娃大厅
            case R.id.layout_tab_zww:
                //getDeviceStates();
                showZwwFg();
                break;
            //排行榜大厅
            case R.id.layout_tab_list:
                showRankFg();
                break;
            //我的大厅
            case R.id.layout_tab_my:
                showMyCenterFg();
                break;
            default:
                break;
        }
    }

    //重写返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
        }
    }

    private void doServcerConnect() {
        String ip = "47.100.8.129";    //123.206.120.46(壕鑫正式)   47.100.8.129(测试)   111.231.74.65 (第一抓娃娃)
        AppClient.getInstance().setHost(ip);
        AppClient.getInstance().setPort(8580);
        if (!AppProperties.initProperties(getResources())) {
            Utils.showLogE(TAG, "netty初始化配置信息出错");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyUtils.socketConnect(getResources(), getApplicationContext());
            }
        }).start();
    }

//    private void getDeviceStates() {
//        UserUtils.doGetDollStatus();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(UserUtils.isUserChanger) {
            UserUtils.isUserChanger = false;
            if((YsdkUtils.loginResult.getData() != null) && (zwwjFragment != null)) {
                UserUtils.NickName = YsdkUtils.loginResult.getData().getAppUser().getNICKNAME();
                UserUtils.USER_ID = YsdkUtils.loginResult.getData().getAppUser().getUSER_ID();
                if(YsdkUtils.loginResult.getData().getSessionID() != null)
                    zwwjFragment.setSessionId(YsdkUtils.loginResult.getData().getSessionID(), false);
            }
            getUserSign(UserUtils.USER_ID,"0"); //签到请求 0 查询签到信息 1签到
        } else {
            //startTimer();
            //getDeviceStates();
            NettyUtils.pingRequest();
        }
    }



    //监控网关区
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_SESSION_INVALID),
            @Tag(Utils.TAG_CONNECT_ERR),
            @Tag(Utils.TAG_GATEWAT_USING),
            @Tag(Utils.TAG_CONNECT_SUCESS)})
    public void getConnectStates(String state) {
        if (state.equals(Utils.TAG_CONNECT_ERR)) {
            Utils.showLogE(TAG, "TAG_CONNECT_ERR");
        } else if (state.equals(Utils.TAG_CONNECT_SUCESS)) {
            Utils.showLogE(TAG, "TAG_CONNECT_SUCESS");
            //getDeviceStates();
        } else if (state.equals(Utils.TAG_SESSION_INVALID)) {
            Utils.showLogE(TAG, "TAG_SESSION_INVALID");
            //TODO 重连后重新连接 QQ/WEIXIN 模式检测
            getYSDKAuthLogin(UserUtils.USER_ID, YsdkUtils.access_token, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL);
        } else if (state.equals(Utils.TAG_GATEWAT_USING)) {
            Utils.showLogE(TAG, "TAG_GATEWAT_USING");
        }
    }

//    //监控全部设备状态区
//    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
//            @Tag(Utils.TAG_GET_DEVICE_STATUS)})
//    public void getDeviceStates(Object response) {
//        if (response instanceof GetStatusResponse) {
//            GetStatusResponse getStatusResponse = (GetStatusResponse) response;
//            Utils.showLogE(TAG, "getStatusResponse=====" + getStatusResponse.getStatus());
//            if (Utils.isEmpty(getStatusResponse.getStatus())) {
//                return;
//            }
//            if ((getStatusResponse.getSeq() != -2)) {
//                if (Utils.isEmpty(getStatusResponse.getStatus())) {
//                    return;
//                }
//                String[] devices = getStatusResponse.getStatus().split(";");
//                for (int i = 0; i < devices.length; i++) {
//                    String[] status = devices[i].split("-");
//                    String address = status[0];
//                    String poohType = status[1];
//                    String stats = status[2];
//                    for (int j = 0; j < roomList.size(); j++) {
//                        RoomBean bean = roomList.get(j);
//                        if (bean.getDollId().equals(address)) {
//                            if (!poohType.equals(Utils.OK)) {
//                                //设备异常了
//                                bean.setDollState("0");
//                            } else {
//                                bean = UserUtils.dealWithRoomStatus(bean, stats);
//                            }
//                            roomList.set(j, bean);
//                        }
//                    }
//                }
//                if(zwwjFragment != null) {
//                    //TODO 按照规则重新排序
//                    Collections.sort(roomList, new Comparator<RoomBean>() {
//                        @Override
//                        public int compare(RoomBean t1, RoomBean t2) {
//                            return t2.getDollState().compareTo(t1.getDollState());
//                        }
//                    });
//                    Utils.showLogE(TAG, "getDeviceStates and notifyAdapter roomList.");
//                    zwwjFragment.notifyAdapter(roomList);
//                }
//            }
//        }
//    }
//
//    private void startTimer() {
//        if (timer == null) {
//            timer = new Timer();
//            timerTask = new timeTask();
//            timer.schedule(timerTask, Utils.GET_STATUS_DELAY_TIME, Utils.GET_STATUS_PRE_TIME);
//        }
//    }
//
//    private void stopTimer() {
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//            timerTask.cancel();
//            timerTask = null;
//        }
//    }
//
//    //定时器区
//    class timeTask extends TimerTask {
//
//        @Override
//        public void run() {
//            NettyUtils.sendGetDeviceStatesCmd();
//        }
//    }

    /** ####################### 网络请求区 #########################  **/

    //自动登录
    private void getYSDKAuthLogin(String userId, String accessToken,String ctype,String channel){
        HttpManager.getInstance().getYSDKAuthLogin(userId, accessToken,ctype,channel, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> loginInfoResult) {
                Log.e(TAG, "断开重连 重新获取相关参数" + loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")) {
                    if ((zwwjFragment != null) && (loginInfoResult.getData() != null)) {
                        zwwjFragment.setSessionId(loginInfoResult.getData().getSessionID(), true);
                    }
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    //房间列表
    private void getDollList(){
        HttpManager.getInstance().getDollList(new RequestSubscriber<Result<RoomListBean>>() {
            @Override
            public void _onSuccess(Result<RoomListBean> roomListBean) {
                if (zwwjFragment != null)
                    zwwjFragment.dismissEmptyLayout();
                if (roomListBean.getMsg().equals("success")) {
                    if (roomListBean.getData() == null) {
                        return;
                    }
                    roomList = roomListBean.getData().getDollList();
                    if (roomList.size() == 0) {
                        if (zwwjFragment != null)
                            zwwjFragment.showError();
                    } else {
                        if (zwwjFragment != null) {
                            for (int i = 0; i < roomList.size(); i++) {
                                RoomBean bean = roomList.get(i);
                                bean = UserUtils.dealWithRoomStatus(bean, bean.getDollState());
                                roomList.set(i, bean);
                            }
                            //TODO 按照规则重新排序
                            Collections.sort(roomList, new Comparator<RoomBean>() {
                                @Override
                                public int compare(RoomBean t1, RoomBean t2) {
                                    return t2.getDollState().compareTo(t1.getDollState());
                                }
                            });
                            zwwjFragment.notifyAdapter(roomList, roomListBean.getData().getPd().getTotalPage());
                        }
                    }
                    initDoConnect();
                }
            }

            @Override
            public void _onError(Throwable e) {
                if(zwwjFragment != null)
                    zwwjFragment.showError();
            }
        });
    }

    private void setSignInDialog(int[] num){
        final SignInDialog signInDialog=new SignInDialog(this,R.style.easy_dialog_style);
        signInDialog.setCancelable(true);
        signInDialog.show();
        signInDialog.setBackGroundColor(num);
        signInDialog.setDialogResultListener(new SignInDialog.DialogResultListener() {
            @Override
            public void getResult(int resultCode) {
                switch (resultCode){
                    case 0:
                        getUserSign(UserUtils.USER_ID,"1");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getSignSuccessDialog(String gold){
        SignSuccessDialog signSuccessDialog=new SignSuccessDialog(this,R.style.easy_dialog_style);
        signSuccessDialog.setCancelable(true);
        signSuccessDialog.show();
        signSuccessDialog.setTextView(gold);
        signSuccessDialog.setDialogResultListener(new SignSuccessDialog.DialogResultListener() {
            @Override
            public void getResult(int resultCode) {

            }
        });
    }

    //签到请求
    private void getUserSign(String userId, final String signType){
        HttpManager.getInstance().getUserSign(userId,signType, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> loginInfoResult) {
                if(loginInfoResult.getMsg().equals("success")){
                    if(signType.equals("0")) {
                        //查询处理
                        isSign=loginInfoResult.getData().getSign().getSIGN_TAG();
                        signNumber = Integer.parseInt(loginInfoResult.getData().getSign().getCSDATE());
                        Utils.showLogE(TAG,"签到天数="+signNumber);
                        for (int i = 0; i < 7; i++) {
                            if (i < signNumber) {
                                signDayNum[i] = 1;
                            } else {
                                signDayNum[i] = 0;
                            }
                        }
                        if(isSign.equals("0")) {
                            setSignInDialog(signDayNum);
                        }
                    }else {
                        //签到处理
                        String signgold=loginInfoResult.getData().getSign().getSIGNGOLD();
                        Utils.showLogE(TAG,"签到赠送金币"+signgold);
                        getSignSuccessDialog(signgold);
                    }
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

}
