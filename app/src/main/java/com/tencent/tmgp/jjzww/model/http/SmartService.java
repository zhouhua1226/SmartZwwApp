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

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Observable<Result<HttpDataInfo>> getLogin(
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
    Observable<Result<HttpDataInfo>> getLoginWithOutCode(
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
    Observable<Result<HttpDataInfo>> getUserPay(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.USEPAYMONEY) String money
    );

    //消费   11/22 16：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERPLAYURL)
    Observable<Result<HttpDataInfo>> getUserPlayNum(
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
    Observable<Result<HttpDataInfo>> getRegPlayBack(
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
    Observable<Result<HttpDataInfo>> getVideoBackList(
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
    Observable<Result<HttpDataInfo>> getUserList();

    //开始游戏分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CREATPLAYLISTURL)
    Observable<Result<HttpDataInfo>> getCreatPlayList(
            @Field(UrlUtils.NICKNANME) String nickName,
            @Field(UrlUtils.DOLLNAME) String dollName
    );

    //围观群众分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.PLAYIDURL)
    Observable<Result<HttpDataInfo>> getPlayId(
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
    Observable<Result<HttpDataInfo>>getConsignee(
            @Field(UrlUtils.CONSIGNEENAME) String name,
            @Field(UrlUtils.CONSIGNEEPHONE) String phone,
            @Field(UrlUtils.CONSIGNEEADDRESS) String address,
            @Field(UrlUtils.CONSIGNEEUSERID) String userID
    );

    //发货
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.SENDGOODSURL)
    Observable<Result<HttpDataInfo>>getSendGoods(
            @Field(UrlUtils.SENDGOODSID) String id,
            @Field(UrlUtils.SENDGOODSNUM) String number,
            @Field(UrlUtils.SENDGOODSSHXX) String consignee,
            @Field(UrlUtils.SENDGOODSREMARK) String remark,
            @Field(UrlUtils.SENDGOODSUSERID) String userId,
            @Field(UrlUtils.SENDGOODSMODE) String mode,
            @Field(UrlUtils.SENDGOODSCOSTNUM) String costNum
    );


    //兑换游戏币
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.EXCHANGEURL)
    Observable<Result<HttpDataInfo>>getExchangeWWB(
            @Field(UrlUtils.SENDGOODSID) String id,
            @Field(UrlUtils.DOLLID) String dollId,
            @Field(UrlUtils.SENDGOODSNUM) String number,
            @Field(UrlUtils.USERID) String userId
    );

    //兑换记录列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.EXCHANGELISTURL)
    Observable<Result<HttpDataInfo>>getExchangeList(
            @Field(UrlUtils.USERID) String userID
    );

    //退出登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGOUT)
    Observable<Result<HttpDataInfo>>getLogout(
            @Field(UrlUtils.USERID) String userID
    );

    //获取用户信息接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETUSERDATEURL)
    Observable<Result<HttpDataInfo>>getUserDate(
            @Field(UrlUtils.USERID) String userId
    );

    //WXQQ登录接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKLOGINURL)
    Observable<Result<HttpDataInfo>>getYSDKLogin(
            @Field(UrlUtils.WXQQ_UID) String uid,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_NICKNAME) String nickName,
            @Field(UrlUtils.WXQQ_IMAGEURL) String imageUrl,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel
    );

    //YSDK支付接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKPAYORDERURL)
    Observable<Result<HttpDataInfo>>getYSDKPay(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_AMOUNT) String amount
    );

    //YSDK自动登录接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.YSDKAUTHLOGINURL)
    Observable<Result<HttpDataInfo>>getYSDKAuthLogin(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel
    );

    //获取充值卡列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.PAYCARDLISTURL)
    Observable<Result<HttpDataInfo>> getPayCardList();

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
    Observable<Result<HttpDataInfo>> getPaymenList(
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
    Observable<Result<HttpDataInfo>> getUserSign(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.SIGNTYPE) String signType
    );

    //获取轮播列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.BANNERURL)
    Observable<Result<HttpDataInfo>> getBannerList();

    //个人排名
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.RANKLISTURL)
    Observable<Result<HttpDataInfo>> getNumRankList(
            @Field(UrlUtils.USERID) String userId
    );

    //物流订单查询
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGISTICSORDERURL)
    Observable<Result<HttpDataInfo>> getLogisticsOrder(
            @Field(UrlUtils.USERID) String userId
    );

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.GETTOYTYPE)
    Observable<Result<HttpDataInfo>> getToyType();

    //房间列表下一页接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETTOYSBYTYPE)
    Observable<Result<RoomListBean>> getToysByType(
            @Field(UrlUtils.CURRENTTYPE) String currentType,
            @Field(UrlUtils.NEXTPAGE) Integer nextPage
    );

    //查询邀请码接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERAWARDCODEURL)
    Observable<Result<HttpDataInfo>> getUserAwardCode(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId
    );

    //兑换邀请码接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.DOAWARDBYUSERCODEURL)
    Observable<Result<HttpDataInfo>> doAwardByUserCode(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.USERAWARDCODE) String awardCode
    );

    //竞猜跑马灯  /pooh-web/app/getGuesserlast10
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.GUESSERLASTTENURL)
    Observable<Result<HttpDataInfo>> getGuesserlast10();

    //竞猜排行
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.RANKBETLISTURL)
    Observable<Result<ListRankBean>> getRankBetList(
            @Field(UrlUtils.USERID) String userId
    );

    //新抓娃娃排行
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.RANKDOLLLISTURL)
    Observable<Result<ListRankBean>> getRankDollList(
            @Field(UrlUtils.USERID) String userId
    );

    //新支付订单接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETPAYORDERURL)
    Observable<Result<HttpDataInfo>>getTradeOrder(
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.WXQQ_ACCESSTOKEN) String accessToken,
            @Field(UrlUtils.WXQQ_PID) String pid,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel
    );

    //查询可推广加盟的接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETPROMOMOTEMANAGE)
    Observable<Result<HttpDataInfo>> getPromomoteManage(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel
    );

    //购买推广加盟的接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.PROMOTEORDERTOGOLD)
    Observable<Result<HttpDataInfo>> getPromomoteOrder(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PROMOTEORDER_PROID) String proManageId,
            @Field(UrlUtils.PROMOTEORDER_PAYTYPE) String payType
    );

    //查询用户已购买的加盟
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETUSERPROMOTEINF)
    Observable<Result<HttpDataInfo>> getUserPromoteInf(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId
    );

    //兑换推广码
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.COMMITUSERPROMOTECODE)
    Observable<Result<HttpDataInfo>> getCommitUserPromoteCode(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PROMOTE_CODE) String promoteCode
    );

    //查询用户现金余额
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETUSERACCBALCOUNT)
    Observable<Result<HttpDataInfo>> getUserAccBalCount(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId
    );

    //用户推广收益明细
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETUSERPROMOTELIST)
    Observable<Result<HttpDataInfo>> getUserPromoteList(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId
    );

    //新查询用户个人信息接口
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETAPPUSERINFURL)
    Observable<Result<HttpDataInfo>> getAppUserInf(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId
    );

    //新获取短信验证吗
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETPHONECODEURL)
    Observable<Result<HttpDataInfo>> getPhoneCode(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.PHONE_SMSTYPE) String smsType,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PHONE_phoneNumber) String phoneNumber
    );

    //用户绑定手机号
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.EDITAPPUSERPHONE)
    Observable<Result<HttpDataInfo>> getEditUserPhone(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.PHONE_SMSTYPE) String smsType,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PHONE_phoneNumber) String phoneNumber,
            @Field(UrlUtils.PHONE_PHONECODE) String phoneCode
    );

    //用户绑定银行卡
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.REGBANKINFURL)
    Observable<Result<HttpDataInfo>> getRegBankInf(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PHONE_SMSTYPE) String smsType,
            @Field(UrlUtils.PHONE_phoneNumber) String phoneNumber,
            @Field(UrlUtils.PHONE_PHONECODE) String phoneCode,
            @Field(UrlUtils.BANK_ADDRESS) String bankAddress,
            @Field(UrlUtils.BANK_NAME) String bankName,
            @Field(UrlUtils.BANK_BRANCH) String bankBranch,
            @Field(UrlUtils.BANK_CARDNO) String bankCardNo,
            @Field(UrlUtils.BANK_IDNUMBER) String idNumber,
            @Field(UrlUtils.USERNAME) String userName
    );

    //用户提现申请
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.DOWITHDRAWCASHURL)
    Observable<Result<HttpDataInfo>> doWithdrawCash(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.PHONE_SMSTYPE) String smsType,
            @Field(UrlUtils.DRAW_ORDERAMT) String orderAmt,
            @Field(UrlUtils.PHONE_PHONECODE) String phoneCode
    );

    //账户收支流水
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.ACCOUNTDETAILURL)
    Observable<Result<HttpDataInfo>> getUserAccountDetailPage(
            @Field(UrlUtils.DEVICETYPE) String deviceType,
            @Field(UrlUtils.OSVERSION) String osVersion,
            @Field(UrlUtils.APPVERSION) String appVersion,
            @Field(UrlUtils.SFID) String sfId,
            @Field(UrlUtils.WXQQ_CTYPE) String ctype,
            @Field(UrlUtils.WXQQ_CHANNEL) String channel,
            @Field(UrlUtils.USERID) String userId,
            @Field(UrlUtils.NEXTPAGE) String nextPage
    );

}
