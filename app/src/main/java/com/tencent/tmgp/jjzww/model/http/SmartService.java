package com.tencent.tmgp.jjzww.model.http;

import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PondResponseBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.bean.RoomListBean;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.utils.UrlUtils;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhouh on 2017/9/8.
 */
public interface SmartService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.VIDEO_GET_TOKEN)
    Observable<Result<Token>> getVideoToken(
            @Field(UrlUtils.APPKEY) String appKey,
            @Field(UrlUtils.APPSECRET) String appSecret);

    //登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGIN)
    Observable<Result<LoginInfo>> getLogin(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.SMSCODE) String code
    );


    //验证码
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETSMSCODE)
    Observable<Result<Token>> getCode(
            @Field(UrlUtils.PHONE) String phone);

    //不需要验证码直接登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGINWITHOUTCODE)
    Observable<Result<LoginInfo>> getLoginWithOutCode(
            @Field(UrlUtils.PHONE) String phone);

    //头像上传
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.FACEIMAGEURL)
    Observable<Result<AppUserBean>> getFaceImage(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.FACEIMAGE) String base64Image
    );

    //修改昵称
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.UserNickNameURL)
    Observable<Result> getNickName(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.NICKNANME) String nickname
    );

    //修改用户名   11/21 13：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERNAMEURL)
        Observable<Result<AppUserBean>> getUserName(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.NICKNANME) String nickName
    );

    //充值   11/22 15：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERPAYURL)
    Observable<Result<LoginInfo>> getUserPay(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.USEPAYMONEY) String money
    );

    //消费   11/22 16：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERPLAYURL)
    Observable<Result<LoginInfo>> getUserPlayNum(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.USERPLAYNUM) String gold,
            @Field(UrlUtils.DOLLID) String dollId

    );

    //ListRank
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.LISTRANKURL)
    Observable<Result<ListRankBean>> getListRank(
    );

    //视屏上传
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.UPLOADURL)
    Observable<Result<LoginInfo>> getRegPlayBack(
            @Field(UrlUtils.TIME) String time,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.STATE) String state,
            @Field(UrlUtils.DOLLID) String dollId,
            @Field(UrlUtils.GUESSID) String guessId


    );

    //获取视频回放列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.VIDEOBACKURL)
    Observable<Result<LoginInfo>> getVideoBackList(
            @Field(UrlUtils.USERID) String userId
    );

    //获取房间用户头像
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CTRLUSERIMAGE)
    Observable<Result<AppUserBean>> getCtrlUserImage(
            @Field(UrlUtils.NICKNANME) String phone
    );

    //下注
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.BETSURL)
    Observable<Result<AppUserBean>> getBets(
            @Field(UrlUtils.USERID) String userID,
            @Field(UrlUtils.WAGER) Integer wager,
            @Field(UrlUtils.GUESSKEY) String guessKey,
            @Field(UrlUtils.GUESSID) String guessId,
            @Field(UrlUtils.DOLLID) String dollID
    );

        //跑马灯
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.getUserList)
    Observable<Result<LoginInfo>> getUserList();

    //开始游戏分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CREATPLAYLISTURL)
    Observable<Result<LoginInfo>> getCreatPlayList(
            @Field(UrlUtils.NICKNANME) String nickName,
            @Field(UrlUtils.DOLLNAME) String dollName
    );

    //围观群众分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.PLAYIDURL)
    Observable<Result<LoginInfo>> getPlayId(
            @Field(UrlUtils.DOLLID) String dollId
    );

    //获取下注人数
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETPONDURL)
    Observable<Result<PondResponseBean>>getPond(
            @Field(UrlUtils.PLAYID) String playId,
            @Field(UrlUtils.DOLLID) String dollId
    );

    //收货人信息
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CONSIGNEEURL)
    Observable<Result<LoginInfo>>getConsignee(
            @Field(UrlUtils.CONSIGNEENAME) String name,
            @Field(UrlUtils.CONSIGNEEPHONE) String phone,
            @Field(UrlUtils.CONSIGNEEADDRESS) String address,
            @Field(UrlUtils.CONSIGNEEUSERID) String userID
    );

    //发货
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.SENDGOODSURL)
    Observable<Result<LoginInfo>>getSendGoods(
            @Field(UrlUtils.SENDGOODSID) String id,
            @Field(UrlUtils.SENDGOODSNUM) String number,
            @Field(UrlUtils.SENDGOODSSHXX) String consignee,
            @Field(UrlUtils.SENDGOODSREMARK) String remark,
            @Field(UrlUtils.SENDGOODSUSERID) String userId,
            @Field(UrlUtils.SENDGOODSMODE) String mode
    );

    //兑换游戏币
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.EXCHANGEURL)
    Observable<Result<LoginInfo>>getExchangeWWB(
            @Field(UrlUtils.SENDGOODSID) String id,
            @Field(UrlUtils.DOLLID) String dollId,
            @Field(UrlUtils.SENDGOODSNUM) String number,
            @Field(UrlUtils.USERID) String userId
    );

    //兑换记录列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.EXCHANGELISTURL)
    Observable<Result<LoginInfo>>getExchangeList(
            @Field(UrlUtils.USERID) String userID
    );

    //退出登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGOUT)
    Observable<Result<LoginInfo>>getLogout(
            @Field(UrlUtils.USERID) String userID
    );

    //获取用户信息接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETUSERDATEURL)
    Observable<Result<LoginInfo>>getUserDate(
            @Field(UrlUtils.USERID) String userId
    );

    //WXQQ登录接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKLOGINURL)
    Observable<Result<LoginInfo>>getYSDKLogin(
            @Field(UrlUtils.WXQQ_UID) String uid,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_NICKNAME) String nickName,
            @Field(UrlUtils.WXQQ_IMAGEURL) String imageUrl
    );

    //YSDK支付接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKPAYORDERURL)
    Observable<Result<LoginInfo>>getYSDKPay(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_AMOUNT) String amount
    );

    //YSDK自动登录接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKAUTHLOGINURL)
    Observable<Result<LoginInfo>>getYSDKAuthLogin(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken
    );

    //获取充值卡列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.PAYCARDLISTURL)
    Observable<Result<LoginInfo>> getPayCardList();

    //用户竞猜记录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETGUESSDETAIL)
    Observable<Result<BetRecordBean>> getGuessDetail(
            @Field(UrlUtils.USERID) String userId
    );

    //用户金币流水  getPaymenlist
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CURRENTACCOUNTURL)
    Observable<Result<LoginInfo>> getPaymenList(
            @Field(UrlUtils.USERID) String userId
    );

    //获取房间列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.DOLLLISTURL)
    Observable<RoomListBean> getDollList();

    //签到接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERSIGNURL)
    Observable<Result<LoginInfo>> getUserSign(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.SIGNTYPE) String signType
    );

    //获取轮播列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.BANNERURL)
    Observable<Result<LoginInfo>> getBannerList();

    //排行榜前十以及个人排名
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.RANKLISTURL)
    Observable<Result<LoginInfo>> getNumRankList(
            @Field(UrlUtils.USERID) String userId
    );

}
