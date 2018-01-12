package com.tencent.tmgp.jjzww.model.http;

import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;
import com.tencent.tmgp.jjzww.bean.ConsigneeBean;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PlayBackBean;
import com.tencent.tmgp.jjzww.bean.PondResponseBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.RoomListBean;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhouh on 2017/9/8.
 */
public class HttpManager {

    private static HttpManager httpManager;
    private Retrofit retrofit;
    private SmartService smartService;

    public static synchronized HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

    private HttpManager() {
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpInstance.getInstance().getClient())
                .baseUrl(UrlUtils.VIDEO_ROOT_URL)
                .build();
        smartService = retrofit.create(SmartService.class);
    }

    public void getVideoToken(String appkey, String appSecret, Subscriber<Result<Token>> subscriber) {
        Observable<Result<Token>> o = smartService.getVideoToken(appkey, appSecret);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //登录
    public void getLogin(String phone, String code, Subscriber<Result<LoginInfo>> subscriber) {
        Observable<Result<LoginInfo>> o = smartService.getLogin(phone, code);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //验证码
    public void getCode(String phone, Subscriber<Result<Token>> subscriber) {
        Observable<Result<Token>> o = smartService.getCode(phone);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //用户直接登录
    public void getLoginWithoutCode(String phone,
                                    Subscriber<Result<LoginInfo>> subscriber) {
        Observable<Result<LoginInfo>> o = smartService.getLoginWithOutCode(phone);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传头像
    public void getFaceImage(String userId, String faceImage, RequestSubscriber<Result<AppUserBean>> subscriber){
        Observable<Result<AppUserBean>> o= smartService.getFaceImage(userId,faceImage);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

//    //修改昵称UserNickNameURL
//    public void getNickName(String phone,String nickName,Subscriber<Result>subscriber){
//        Observable<Result<LoginInfo>> o= smartService.getFaceImage(phone,nickName);
//        o.subscribeOn(Schedulers.newThread())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    //修改用户名   11/21 13：15
    public void getUserName(String userId,String nickName,Subscriber<Result<AppUserBean>> subscriber){
        Observable<Result<AppUserBean>> o= smartService.getUserName(userId,nickName);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //充值   11/21 15：15
    public void getUserPay(String phone,String money,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o= smartService.getUserPay(phone,money);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //消费   11/21 16：15
    public void getUserPlayNum(String userId,String money,String dollId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o= smartService.getUserPlayNum(userId,money,dollId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //ListRank
    public void getListRank(Subscriber<Result<ListRankBean>> subscriber){
        Observable<Result<ListRankBean>> o= smartService.getListRank();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //视屏上传
    public void getRegPlayBack(String time,String userId,String state,String dollId,String guessId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o= smartService.getRegPlayBack(time,userId,state,dollId,guessId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取视频回放列表
    public void getVideoBackList(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o= smartService.getVideoBackList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取房间用户头像
    public void getCtrlUserImage(String phone,Subscriber<Result<AppUserBean>> subscriber){
        Observable<Result<AppUserBean>> o= smartService.getCtrlUserImage(phone);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //下注
    public void getBets(String userId,int wager,String guessKey,String guessId,
                        String dollID,Subscriber<Result<AppUserBean>> subscriber){
        Observable<Result<AppUserBean>> o= smartService.getBets(userId,wager,guessKey,guessId,dollID);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //跑马灯
    public void getUserList(Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o= smartService.getUserList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //游戏场次
    public void getPlayId(String dollname,Subscriber<Result<LoginInfo>>subscriber){
        Observable<Result<LoginInfo>> o =smartService.getPlayId(dollname);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //开始游戏创建场次
    public void  getCreatPlayList(String nickName,String dollname,Subscriber<Result<LoginInfo>>subscriber){
        Observable<Result<LoginInfo>> o =smartService.getCreatPlayList(nickName,dollname);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取下注人数
    public void  getPond(String playId,String dollId,Subscriber<Result<PondResponseBean>>subscriber){
        Observable<Result<PondResponseBean>> o =smartService.getPond(playId,dollId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //收货人信息
    public void getConsignee(String name,String phone,String address,String userID,Subscriber<Result<LoginInfo>>subscriber){
        Observable<Result<LoginInfo>> o =smartService.getConsignee(name,phone,address,userID);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //发货
    public void getSendGoods(String id,String number,String consignee,String remark,String userID,String mode,Subscriber<Result<LoginInfo>>subscriber){
        Observable<Result<LoginInfo>> o =smartService.getSendGoods(id,number,consignee,remark,userID,mode);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //兑换游戏币
    public void getExChangeWWB(String id,String dollId,String number,String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getExchangeWWB(id,dollId,number,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取兑换记录列表
    public void getExChangeList(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getExchangeList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //退出登录
    public void getLogout(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getLogout(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取用户信息
    public void getUserDate(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getUserDate(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //WXQQ登录
    public void getYSDKLogin(String uid,String accessToken,String nickName,String imageUrl,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getYSDKLogin(uid,accessToken,nickName,imageUrl);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //ysdk支付创建订单接口
    public void getYSDKPay(String userId,String accessToken,String amount,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getYSDKPay(userId,accessToken,amount);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //YSDK自动登录接口
    public void getYSDKAuthLogin(String userId,String accessToken,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getYSDKAuthLogin(userId,accessToken);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取充值卡列表
    public void getPayCardList(Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getPayCardList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //用户竞猜记录

    public void getGuessDetail(String userId,Subscriber<Result<BetRecordBean>>subscriber){
        Observable<Result<BetRecordBean>> o =smartService.getGuessDetail(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取金币流水列表
    public void getPaymenList(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getPaymenList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取房间列表
    public void getDollList(Subscriber<RoomListBean> subscriber){
        Observable<RoomListBean> o =smartService.getDollList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //签到
    public void getUserSign(String userId,String signType,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getUserSign(userId,signType);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取轮播列表
    public void getBannerList(Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getBannerList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //新排行榜接口
    public void getNumRankList(String userId,Subscriber<Result<LoginInfo>> subscriber){
        Observable<Result<LoginInfo>> o =smartService.getNumRankList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
