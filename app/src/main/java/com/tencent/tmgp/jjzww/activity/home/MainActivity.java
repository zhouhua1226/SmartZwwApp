package com.tencent.tmgp.jjzww.activity.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.bean.ZwwRoomBean;
import com.tencent.tmgp.jjzww.fragment.MyCenterFragment;
import com.tencent.tmgp.jjzww.fragment.RankFragmentTwo;
import com.tencent.tmgp.jjzww.fragment.ZWWJFragment;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.EmptyLayout;
import com.tencent.tmgp.jjzww.view.LoginDialog;
import com.gatz.netty.AppClient;
import com.gatz.netty.utils.AppProperties;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.GetStatusResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity-";

    @BindView(R.id.iv_tab_zww)
    ImageView ivTabZww;//娃娃机图标
    @BindView(R.id.tv_tab_hall)
    TextView tvTabHall;//娃娃机文字
    @BindView(R.id.layout_tab_zww)
    LinearLayout layoutTabZww;//娃娃机图标布局
    @BindView(R.id.iv_tab_list)
    ImageView ivTabList;//排行榜图标
    @BindView(R.id.tv_tab_list)
    TextView tvTabList;//排行旁文字
    @BindView(R.id.layout_tab_list)
    LinearLayout layoutTabList;//排行旁图标布局
    @BindView(R.id.iv_tab_my)
    ImageView ivTabMy;//我的图标
    @BindView(R.id.tv_tab_my)
    TextView tvTabMy;//我的文字
    @BindView(R.id.layout_tab_my)
    LinearLayout layoutTabMy;//我的图标布局
    @BindView(R.id.lv_main_bottom)
    LinearLayout lvMainBottom;
    @BindView(R.id.main_center)
    FrameLayout mainCenter;

    private LoginDialog loginDialog;
    private Timer timer;
    private TimerTask timerTask;
    private MyCenterFragment myCenterFragment;//个人中心
    private RankFragmentTwo rankFragment;//排行榜
    private ZWWJFragment zwwjFragment;//抓娃娃
    private long mExitTime;
    private List<ZwwRoomBean> dollLists = new ArrayList<>();
    private String ph;
    private List<VideoBackBean> playBackBeanList = new ArrayList<>();
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Utils.showLogE(TAG, "afterCreate");
        initWelcome();
        settings = getSharedPreferences("app_user", 0);// 获取SharedPreference对象
        editor = settings.edit();// 获取编辑对象。
        editor.putBoolean("isVibrator",true);
        editor.commit();
    }

    private void initWelcome() {
        setContentView(R.layout.activity_welcome);//闪屏
        new Handler().postDelayed(initRunnable, 2000);
    }

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            View MainView = getLayoutInflater().inflate(R.layout.activity_main, null);
            setContentView(MainView);
            initView();
            showZwwFg();
            initData();
        }
    };

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initData() {
        //RxBus.get().register(this);
        loginDialog = new LoginDialog(this, R.style.easy_dialog_style);
        loginDialog.setDialogClickListener(idialogClick);
        doServcerConnect();
        if ((boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_LOGIN, false)) {
            //用户已经注册
            ph = (String) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_PHONE, "");
            if (Utils.isEmpty(ph)) {
                return;
            }
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                logIn(ph, false);
            }
        } else {
            loginDialog.setCanceledOnTouchOutside(false);
            loginDialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.isExit = true;
        stopTimer();
        //RxBus.get().unregister(this);
    }

    private void logIn(String phone, boolean isShow) {
        if (isShow) {
            zwwjFragment.showLoading();
        }
        Utils.showLogE(TAG, "logIn::::" + phone);
        String str = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
        HttpManager.getInstance().getLoginWithoutCode(str, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                //zwwjFragment.dismissEmptyLayout();
                Utils.showLogE(TAG, "logIn::::" + loginInfoResult.getMsg());
                Utils.token = loginInfoResult.getData().getAccessToken();
                dollLists = loginInfoResult.getData().getDollList();
                //用户手机号
                UserUtils.UserPhone = loginInfoResult.getData().getAppUser().getPHONE();
                //UserUtils.UserNickName = loginInfoResult.getData().getAppUser().getPHONE();
                //用户名  11/22 13：25
                UserUtils.UserName = loginInfoResult.getData().getAppUser().getUSERNAME();
                UserUtils.NickName = loginInfoResult.getData().getAppUser().getNICKNAME();
                //用户余额
                UserUtils.UserBalance = loginInfoResult.getData().getAppUser().getBALANCE();
                //用户头像  11/22 13：25
                UserUtils.UserImage = UrlUtils.USERFACEIMAGEURL + loginInfoResult.getData().getAppUser().getIMAGE_URL();
                UserUtils.DOLL_ID = loginInfoResult.getData().getAppUser().getDOLL_ID();
                UserUtils.USER_ID = loginInfoResult.getData().getAppUser().getUSER_ID();
                UserUtils.UserAddress=loginInfoResult.getData().getAppUser().getCNEE_NAME()+" "+
                                      loginInfoResult.getData().getAppUser().getCNEE_PHONE()+" "+
                                      loginInfoResult.getData().getAppUser().getCNEE_ADDRESS();
                zwwjFragment.setSessionId(loginInfoResult.getData().getSessionID());
                if (dollLists.size() != 0) {
                    zwwjFragment.notifyAdapter(dollLists);
                }
                getDeviceStates();
                startTimer();
            }

            @Override
            public void _onError(Throwable e) {
                if (zwwjFragment != null) {
                    zwwjFragment.showError();
                }
                Utils.showLogE(TAG, "logIn::::" + e.getMessage());
            }
        });
    }

    private LoginDialog.IdialogClick idialogClick = new LoginDialog.IdialogClick() {
        @Override
        public void getAuthCode(String phone) {
            //TODO 获取验证码
            String str = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
            HttpManager.getInstance().getCode(str, new RequestSubscriber<Result<Token>>() {
                @Override
                public void _onSuccess(Result<Token> result) {
                    Utils.showLogE(TAG, "getAuthCode::::" + result.toString());
                }

                @Override
                public void _onError(Throwable e) {
                    Utils.showLogE(TAG, "getAuthCode::::" + e.getMessage());
                }
            });
        }

        @Override
        public void login(final String phone, String code) {
            zwwjFragment.showLoading();
            String str = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
            String str1 = Base64.encodeToString(code.getBytes(), Base64.DEFAULT);
            //TODO 登录
            HttpManager.getInstance().getLogin(str, str1, new RequestSubscriber<Result<LoginInfo>>() {
                @Override
                public void _onSuccess(Result<LoginInfo> result) {
                    zwwjFragment.dismissEmptyLayout();
                    if (result.getMsg().equals(Utils.HTTP_OK)) {
                        Utils.showLogE(TAG, "logInWithSMS::::" + result.getMsg());
                        dollLists = result.getData().getDollList();
                        Utils.token = result.getData().getAccessToken();
                        SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_LOGIN, true);
                        SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_PHONE, phone);
                        UserUtils.UserPhone = phone;
                        UserUtils.UserName = result.getData().getAppUser().getUSERNAME();
                        UserUtils.NickName = result.getData().getAppUser().getNICKNAME();
                        UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                        UserUtils.UserImage = UrlUtils.USERFACEIMAGEURL + result.getData().getAppUser().getIMAGE_URL();
                        UserUtils.DOLL_ID=result.getData().getAppUser().getDOLL_ID();
                        UserUtils.USER_ID=result.getData().getAppUser().getUSER_ID();
                        UserUtils.UserAddress=result.getData().getAppUser().getCNEE_NAME()+" "+
                                              result.getData().getAppUser().getCNEE_PHONE()+" "+
                                              result.getData().getAppUser().getCNEE_ADDRESS();
                        zwwjFragment.setSessionId(result.getData().getSessionID());
                        if (dollLists.size() == 0) {
                            zwwjFragment.showError();
                        } else {
                            zwwjFragment.notifyAdapter(dollLists);
                        }
                        getDeviceStates();
                        startTimer();
                        Utils.showLogE(TAG, "afterCreate:::::>>>>" + dollLists.size());
                    }
                }

                @Override
                public void _onError(Throwable e) {
                    zwwjFragment.showError();
                    Utils.showLogE(TAG, "getLogin::::" + e.getMessage());
                }
            });
        }

        @Override
        public void dialogMiss() {
            finish();
            System.exit(0);
        }
    };

    /**
     * 设置未选中状态
     */
    private void setFocuse() {
        ivTabZww.setBackgroundResource(R.drawable.zww1);
        tvTabHall.setTextColor(getResources().getColor(R.color.main_gray));
        ivTabList.setBackgroundResource(R.drawable.phb);
        tvTabList.setTextColor(getResources().getColor(R.color.main_gray));
        ivTabMy.setBackgroundResource(R.drawable.wd);
        tvTabMy.setTextColor(getResources().getColor(R.color.main_gray));
    }

    private EmptyLayout.OnClickReTryListener onClickReTryListener = new EmptyLayout.OnClickReTryListener() {
        @Override
        public void onClickReTry(View view) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                logIn(ph, false);
            }
        }
    };

    private void showZwwFg() {
        FragmentTransaction nowTransaction = getSupportFragmentManager().beginTransaction();
        if (zwwjFragment == null) {
            zwwjFragment = new ZWWJFragment();
            zwwjFragment.setOnClickEmptyListener(onClickReTryListener);
        }
        nowTransaction.replace(R.id.main_center, zwwjFragment);
        nowTransaction.commitAllowingStateLoss();
        setFocuse();
        ivTabZww.setBackgroundResource(R.drawable.zww);
        tvTabHall.setTextColor(getResources().getColor(R.color.pink));
    }

    private void showRankFg() {
        FragmentTransaction nowTransaction = getSupportFragmentManager().beginTransaction();
        if (rankFragment == null) {
            rankFragment = new RankFragmentTwo();
        }
        nowTransaction.replace(R.id.main_center, rankFragment);
        nowTransaction.commitAllowingStateLoss();
        setFocuse();
        ivTabList.setBackgroundResource(R.drawable.phb1);
        tvTabList.setTextColor(getResources().getColor(R.color.pink));
    }

    private void showMyCenterFg() {
        FragmentTransaction nowTransaction = getSupportFragmentManager().beginTransaction();
        if (myCenterFragment == null) {
            myCenterFragment = new MyCenterFragment();
        }
        nowTransaction.replace(R.id.main_center, myCenterFragment);
        nowTransaction.commitAllowingStateLoss();
        setFocuse();
        ivTabMy.setBackgroundResource(R.drawable.wd1);
        tvTabMy.setTextColor(getResources().getColor(R.color.pink));
    }

    @OnClick({R.id.layout_tab_zww, R.id.layout_tab_list, R.id.layout_tab_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //抓娃娃大厅
            case R.id.layout_tab_zww:
                getDeviceStates();
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
            finish();
            System.exit(0);
        }
    }

    private void doServcerConnect() {
        String ip = "47.100.8.129";//"106.75.142.42";//"47.100.8.129";//172.16.7.222测试
        AppClient.getInstance().setHost(ip);
        AppClient.getInstance().setPort(8580); //
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

    private void getDeviceStates() {
        UserUtils.doGetDollStatus();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer();
        getDeviceStates();
        NettyUtils.pingRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
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
            getDeviceStates();
        } else if (state.equals(Utils.TAG_SESSION_INVALID)) {
            Utils.showLogE(TAG, "TAG_SESSION_INVALID");
            logIn((String) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_PHONE, "0"), false);
        } else if (state.equals(Utils.TAG_GATEWAT_USING)) {
            Utils.showLogE(TAG, "TAG_GATEWAT_USING");
        }
    }

    //监控全部设备状态区
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_GET_DEVICE_STATUS)})
    public void getDeviceStates(Object response) {
        if (response instanceof GetStatusResponse) {
            GetStatusResponse getStatusResponse = (GetStatusResponse) response;
            Utils.showLogE(TAG, "getStatusResponse=====" + getStatusResponse.getStatus());
            if (Utils.isEmpty(getStatusResponse.getStatus())) {
                return;
            }
            if ((getStatusResponse.getSeq() != -2)) {
                if (Utils.isEmpty(getStatusResponse.getStatus())) {
                    return;
                }
                String[] devices = getStatusResponse.getStatus().split(";");
                for (int i = 0; i < devices.length; i++) {
                    String[] status = devices[i].split("-");
                    String address = status[0];
                    String poohType = status[1];
                    String stats = status[2];
                    for (int j = 0; j < dollLists.size(); j++) {
                        ZwwRoomBean bean = dollLists.get(j);
                        if (bean.getDOLL_ID().equals(address)) {
                            if (!poohType.equals(Utils.OK)) {
                                //设备异常了
                                bean.setDOLL_STATE("0");
                            } else {
                                if (stats.equals(Utils.FREE)) {
                                    bean.setDOLL_STATE("10");
                                } else if (stats.equals(Utils.BUSY)) {
                                    bean.setDOLL_STATE("11");
                                }
                            }
                            dollLists.set(j, bean);
                        }
                    }
                    //TODO 按照规则重新排序
                    Collections.sort(dollLists);
                    zwwjFragment.notifyAdapter(dollLists);
                }
            }
        }
    }

    //监控单个网关连接区
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_DISCONNECT)})
    public void getSingleGatwayDisConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayDisConnect id" + id);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_CONNECT)})
    public void getSingleGatwayConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayConnect id" + id);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timerTask = new timeTask();
            timer.schedule(timerTask, Utils.GET_STATUS_DELAY_TIME, Utils.GET_STATUS_PRE_TIME);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            timerTask.cancel();
            timerTask = null;
        }
    }

    //定时器区
    class timeTask extends TimerTask {

        @Override
        public void run() {
            NettyUtils.sendGetDeviceStatesCmd();
        }
    }


}
