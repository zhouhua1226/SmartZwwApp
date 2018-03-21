package com.tencent.tmgp.jjzww.model.http;

import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.PondResponseBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.RoomListBean;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;

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
    public void getLogin(String phone, String code, Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getLogin(phone, code);
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
                                    Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getLoginWithOutCode(phone);
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
//        Observable<Result<HttpDataInfo>> o= smartService.getFaceImage(phone,nickName);
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
    public void getUserPay(String phone,String money,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o= smartService.getUserPay(phone,money);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //消费   11/21 16：15
    public void getUserPlayNum(String userId,String money,String dollId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o= smartService.getUserPlayNum(userId,money,dollId);
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
    public void getRegPlayBack(String time,String userId,String state,String dollId,String guessId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o= smartService.getRegPlayBack(time,userId,state,dollId,guessId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取视频回放列表
    public void getVideoBackList(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o= smartService.getVideoBackList(userId);
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
    public void getUserList(Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o= smartService.getUserList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //游戏场次
    public void getPlayId(String dollname,Subscriber<Result<HttpDataInfo>>subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getPlayId(dollname);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //开始游戏创建场次
    public void  getCreatPlayList(String nickName,String dollname,Subscriber<Result<HttpDataInfo>>subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getCreatPlayList(nickName,dollname);
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
    public void getConsignee(String name,String phone,String address,String userID,Subscriber<Result<HttpDataInfo>>subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getConsignee(name,phone,address,userID);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //发货
    public void getSendGoods(String id,String number,String consignee,String remark,String userID,String mode,String costNum,Subscriber<Result<HttpDataInfo>>subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getSendGoods(id,number,consignee,remark,userID,mode,costNum);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //兑换游戏币
    public void getExChangeWWB(String id,String dollId,String number,String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getExchangeWWB(id,dollId,number,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取兑换记录列表
    public void getExChangeList(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getExchangeList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //退出登录
    public void getLogout(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getLogout(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取用户信息
    public void getUserDate(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getUserDate(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //WXQQ登录
    public void getYSDKLogin(String uid,String accessToken,String nickName,String imageUrl,String ctype,String channel,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getYSDKLogin(uid,accessToken,nickName,imageUrl,ctype,channel);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //ysdk支付创建订单接口
    public void getYSDKPay(String userId,String accessToken,String amount,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getYSDKPay(userId,accessToken,amount);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //YSDK自动登录接口
    public void getYSDKAuthLogin(String userId,String accessToken,String ctype,String channel,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getYSDKAuthLogin(userId,accessToken,ctype,channel);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取充值卡列表
    public void getPayCardList(Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getPayCardList();
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
    public void getPaymenList(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getPaymenList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取房间列表
    public void getDollList(Subscriber<Result<RoomListBean>> subscriber){
        Observable<Result<RoomListBean>> o =smartService.getToysByType("", 1);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //签到
    public void getUserSign(String userId,String signType,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getUserSign(userId,signType);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取轮播列表
    public void getBannerList(Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getBannerList();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //排行榜当前用户排名接口
    public void getNumRankList(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getNumRankList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //物流订单接口
    public void getLogisticsOrder(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o =smartService.getLogisticsOrder(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取类型
    public void getToyType(Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o =smartService.getToyType();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取分类娃娃机
    public void getToyListByType(String type, int page, Subscriber<Result<RoomListBean>> subscriber) {
        Observable<Result<RoomListBean>> o = smartService.getToysByType(type, page);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询邀请码接口
    public void getUserAwardCode(String userId, Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getUserAwardCode(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL, userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //兑换邀请码接口
    public void doAwardByUserCode(String userId,String awardCode, Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.doAwardByUserCode(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL, userId,awardCode);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //竞猜跑马灯
    public void getGuesserlast10(Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o =smartService.getGuesserlast10();
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //竞猜排行榜
    public void getRankBetList(String userId,Subscriber<Result<ListRankBean>> subscriber){
        Observable<Result<ListRankBean>> o =smartService.getRankBetList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //抓娃娃排行榜
    public void getRankDollList(String userId,Subscriber<Result<ListRankBean>> subscriber){
        Observable<Result<ListRankBean>> o =smartService.getRankDollList(userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取支付订单号接口
    public void getTradeOrder(String userId,String accessToken,String pid, Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getTradeOrder(userId,accessToken,pid,
                 UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询可推广加盟的接口
    public void getPromomoteManage( Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getPromomoteManage(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //购买加盟接口
    public void getPromomoteOrder(String userId,String proManageId,String payType,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getPromomoteOrder(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId,proManageId,payType);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询已购买的加盟
    public void getUserPromoteInf(String userId, Subscriber<Result<HttpDataInfo>> subscriber) {
        Observable<Result<HttpDataInfo>> o = smartService.getUserPromoteInf(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //兑换推广码
    public void getCommitUserPromoteCode(String userId,String promoteCode,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getCommitUserPromoteCode(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId,promoteCode);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询账户余额
    public void getUserAccBalCount(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getUserAccBalCount(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //推广收益明细
    public void getUserPromoteList(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getUserPromoteList(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //新查询用户个人信息接口
    public void getAppUserInf(String userId,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getAppUserInf(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,userId);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //新获取验证码
    public void getPhoneCode(String userId,String phoneNumber,String smsType,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getPhoneCode(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,smsType,
                userId,phoneNumber);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //用户绑定手机号
    public void getEditUserPhone(String userId,String phoneNumber,String phoneCode,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getEditUserPhone(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,UrlUtils.PHONE_SMS_TYPE_2000,
                userId,phoneNumber,phoneCode);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //用户绑定银行卡
    public void getRegBankInf(String userId,String smsType,String phoneNumber,String phoneCode,String bankAddress,String bankName,String bankBranch,String bankCardNo,String idNumber,String userName,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getRegBankInf(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,
                userId,smsType,phoneNumber,phoneCode,bankAddress,bankName,bankBranch,bankCardNo,idNumber,userName);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //提现接口
    public void doWithdrawCash(String userId,String smsType,String orderAmt,String phoneCode,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.doWithdrawCash(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,
                userId,smsType,orderAmt,phoneCode);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //账户收支流水  getUserAccountDetailPage
    public void getUserAccountDetailPage(String userId,String nextPage,Subscriber<Result<HttpDataInfo>> subscriber){
        Observable<Result<HttpDataInfo>> o = smartService.getUserAccountDetailPage(Utils.deviceType, Utils.osVersion,
                Utils.appVersion,Utils.IMEI, UrlUtils.LOGIN_CTYPE,UrlUtils.LOGIN_CHANNEL,
                userId,nextPage);
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
