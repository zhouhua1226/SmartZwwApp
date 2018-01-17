package com.tencent.tmgp.jjzww.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gatz.netty.utils.NettyUtils;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.view.CtrlActivity;
import com.tencent.tmgp.jjzww.activity.home.NewsWebActivity;
import com.tencent.tmgp.jjzww.adapter.ZWWAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.BannerBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Marquee;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.EmptyLayout;
import com.tencent.tmgp.jjzww.view.GlideImageLoader;
import com.tencent.tmgp.jjzww.view.MarqueeView;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.PullToRefreshView;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class ZWWJFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private static final String TAG = "ZWWJFragment";
    @BindView(R.id.zww_recyclerview)
    RecyclerView zwwRecyclerview;
    @BindView(R.id.zww_emptylayout)
    EmptyLayout zwwEmptylayout;
    Unbinder unbinder;
    @BindView(R.id.marqueeview)
    MarqueeView marqueeview;
    @BindView(R.id.zww_banner)
    Banner zwwBanner;
    @BindView(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;
    Unbinder unbinder1;

    private List<RoomBean> roomBeens = new ArrayList<>();
    private ZWWAdapter zwwAdapter;
    private String sessionId;
    private EmptyLayout.OnClickReTryListener onClickReTryListener;
    private List<VideoBackBean> playBackBeanList = new ArrayList<>();
    private List<Marquee> marquees = new ArrayList<>();
    private List<BannerBean> bannerList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zww;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        init();
        initData();
        onClick();
        getUserList();
        getBannerList();

    }

    private void init() {

//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        }
    }

    private void getUserList() {
        HttpManager.getInstance().getUserList(new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> listRankBeanResult) {
                playBackBeanList = listRankBeanResult.getData().getPlayback();
                for (int i = 0; i < playBackBeanList.size(); i++) {
                    Marquee marquee = new Marquee();
                    String s = "恭喜" + "<font color='#FF0000'>" + playBackBeanList.get(i).getNICKNAME() + "</font>"
                            + "用户抓中一个" + playBackBeanList.get(i).getDOLL_NAME();
                    marquee.setTitle(s);
                    marquee.setImgUrl(UrlUtils.APPPICTERURL + playBackBeanList.get(i).getIMAGE_URL());
                    marquees.add(marquee);
                }
                marqueeview.setImage(true);
                marqueeview.startWithList(marquees);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


    private void initData() {
        Utils.showLogE(TAG, "afterCreate:::::>>>>" + roomBeens.size());
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        dismissEmptyLayout();
        zwwAdapter = new ZWWAdapter(getActivity(), roomBeens);
        zwwRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        zwwRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        zwwRecyclerview.setHasFixedSize(true);
        zwwRecyclerview.setNestedScrollingEnabled(false);
        zwwRecyclerview.setAdapter(zwwAdapter);
        if (onClickReTryListener != null) {
            zwwEmptylayout.setOnClickReTryListener(onClickReTryListener);
        }
    }

    private void onClick() {
        zwwAdapter.setmOnItemClickListener(onItemClickListener);
    }

    public void notifyAdapter(List<RoomBean> rooms) {
        roomBeens = rooms;
        zwwAdapter.notify(roomBeens);
    }

    public void setOnClickEmptyListener(EmptyLayout.OnClickReTryListener o) {
        this.onClickReTryListener = o;
    }

    public void showError() {
        zwwEmptylayout.showEmpty();
    }

    public void showLoading() {
        zwwEmptylayout.showLoading();
    }

    public void dismissEmptyLayout() {
        zwwEmptylayout.dismiss();
    }

    public void setSessionId(String id, boolean isReconnect) {
        this.sessionId = id;
        UserUtils.setNettyInfo(sessionId, UserUtils.USER_ID, "", isReconnect);
        UserUtils.doNettyConnect(NettyUtils.LOGIN_TYPE_TENCENT);
    }

    public ZWWAdapter.OnItemClickListener onItemClickListener =
            new ZWWAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if ((roomBeens.size() > 0) && (!Utils.isEmpty(sessionId))) {
                        String room_id = roomBeens.get(position).getDollId();
                        boolean room_status = false;
                        UserUtils.setNettyInfo(sessionId, UserUtils.USER_ID, room_id, false);
                        if (roomBeens.get(position).getDollState().equals("0")) {
                            room_status = true;
                        } else if (roomBeens.get(position).getDollState().equals("1")) {
                            room_status = false;
                        }
                        String rtmpUrl1 = roomBeens.get(position).getCameras().get(0).getRtmpUrl();
                        String rtmpUrl2 = roomBeens.get(position).getCameras().get(1).getRtmpUrl();
                        String serviceName1 = roomBeens.get(position).getCameras().get(0).getServerName();
                        String serviceName2 = roomBeens.get(position).getCameras().get(1).getServerName();
                        String liveStream1 = roomBeens.get(position).getCameras().get(0).getLivestream();
                        String liveStream2 = roomBeens.get(position).getCameras().get(1).getLivestream();
                        String idToken = "?token=" + UserUtils.SRSToken.getToken()
                                + "&expire=" + UserUtils.SRSToken.getExpire()
                                + "&tid=" + UserUtils.SRSToken.getTid()
                                + "&time=" + UserUtils.SRSToken.getTime()
                                + "&type=" + UserUtils.SRSToken.getType()
                                + "/";
                        String url1 = rtmpUrl1 + serviceName1 + idToken + liveStream1;
                        String url2 = rtmpUrl2 + serviceName2 + idToken + liveStream2;
                        Utils.showLogE(TAG, "房间推流地址1=" + url1);
                        Utils.showLogE(TAG, "房间推流地址2=" + url2);
                        if (!TextUtils.isEmpty(url2) && !TextUtils.isEmpty(url1)) {
                            enterNext(roomBeens.get(position).getDollName(),
                                    url1, url2,
                                    room_status,
                                    String.valueOf(roomBeens.get(position).getDollGold()),
                                    roomBeens.get(position).getDollId());
                        } else {
                            Utils.showLogE(TAG, "当前设备没有配置摄像头!");
                        }
                    }
                }
            };

    private void enterNext(String name, String camera1, String camera2, boolean status, String gold, String id) {
        Intent intent = new Intent(getActivity(), CtrlActivity.class);
        intent.putExtra(Utils.TAG_ROOM_NAME, name);
        intent.putExtra(Utils.TAG_URL_MASTER, camera1);
        intent.putExtra(Utils.TAG_URL_SECOND, camera2);
        intent.putExtra(Utils.TAG_ROOM_STATUS, status);
        intent.putExtra(Utils.TAG_DOLL_GOLD, gold);
        intent.putExtra(Utils.TAG_DOLL_Id, id);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        //getUserList();
    }

    //banner轮播
    private void initBanner(List<?> list) {
        //设置Banner样式
        zwwBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        zwwBanner.setImageLoader(new GlideImageLoader());
        zwwBanner.setImages(list);
        //设置Banner动画效果
        zwwBanner.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        zwwBanner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        zwwBanner.setIndicatorGravity(BannerConfig.CENTER);
        //Banner设置方法全部调用完毕时最后调用
        zwwBanner.start();
        zwwBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //MyToast.getToast(getContext(), "您点击了第" + (position + 1) + "张图片").show();
                //startActivity(new Intent(getContext(), NewsWebActivity.class));
            }
        });
    }

    private void getBannerList() {
        HttpManager.getInstance().getBannerList(new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Utils.showLogE(TAG, "获取轮播列表=" + loginInfoResult.getMsg());
                if (loginInfoResult.getMsg().equals("success")) {
                    bannerList = loginInfoResult.getData().getRunImage();
                    if (bannerList.size() > 0) {
                        for (int i = 0; i < bannerList.size(); i++) {
                            list.add(UrlUtils.APPPICTERURL + bannerList.get(i).getIMAGE_URL());
                        }
                        initBanner(list);
                    }
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        zwwBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        zwwBanner.stopAutoPlay();
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isNetworkAvailable(getContext())) {
                   // MyToast.getToast(getContext(), "上拉加载下一页").show();
                } else {
                    MyToast.getToast(getContext(), "网络连接异常，请检查网络").show();
                }
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        if(mPullToRefreshView!=null)
            mPullToRefreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Utils.isNetworkAvailable(getContext())) {

                    } else {
                        MyToast.getToast(getContext(), "网络连接异常，请检查网络").show();
                    }
                    mPullToRefreshView.onHeaderRefreshComplete();
                }
            }, 1500);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
