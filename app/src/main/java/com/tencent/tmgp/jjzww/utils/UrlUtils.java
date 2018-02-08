package com.tencent.tmgp.jjzww.utils;

/**
 * Created by zhouh on 2017/9/8.
 */
public class UrlUtils {
    //视频url
    public static final String VIDEO_ROOT_URL = "https://open.ys7.com/";
    public static final String VIDEO_GET_TOKEN = "api/lapp/token/get";
    //getToken
    public static final String APPKEY = "appKey";
    public static final String APPSECRET = "appSecret";

    public static final String ID = "id";
    public static final String PHONE = "phone";
    public static final String SMSCODE = "code";
    public static final String FACEIMAGE = "base64Image";
    public static final String NICKNANME = "nickName";
    public static final String USERNAME = "userName";
    public static final String USEPAYMONEY = "money";
    public static final String USERPLAYNUM = "gold";
    public static final String DOLLNAME = "dollName";
    public static final String TIME = "time";
    public static final String STATE = "state";
    public static final String GUESSID="guessId";

    public static final String USERID = "userId";
    public static final String WAGER = "wager";
    public static final String GUESSKEY = "guessKey";
    public static final String PLAYBACK = "playBackId";
    public static final String DOLLID = "dollId";
    public static final String PLAYID = "playId";
    //设置收货人信息接口字段
    public static final String CONSIGNEENAME="name";
    public static final String CONSIGNEEPHONE="phone";
    public static final String CONSIGNEEADDRESS="address";
    public static final String CONSIGNEEUSERID="userId";
    //设置发货接口字段
    public static final String SENDGOODSID="id";
    public static final String SENDGOODSNUM="number";
    public static final String SENDGOODSSHXX="consignee";
    public static final String SENDGOODSREMARK="remark";
    public static final String SENDGOODSUSERID="userId";
    public static final String SENDGOODSMODE="mode";
    public static final String SENDGOODSCOSTNUM="costNum";

    //微信QQ登录接口
    public static final String WXQQ_UID="uid";
    public static final String WXQQ_ACCESSTOKEN="accessToken";
    public static final String WXQQ_NICKNAME="nickName";
    public static final String WXQQ_IMAGEURL="imageUrl";
    public static final String WXQQ_CTYPE="ctype";
    public static final String WXQQ_CHANNEL="channel";
    public static final String LOGIN_CTYPE="YSDK";
    public static final String LOGIN_CHANNEL="ANDROID";
    //ysdk支付金额字段
    public static final String WXQQ_AMOUNT="amount";
    //签到接口
    public static final String SIGNTYPE="signType";
    //分类
    public static final String NEXTPAGE = "nextPage";
    public static final String CURRENTTYPE = "currentType";
    //邀请码
    public static final String USERAWARDCODE = "awardCode";
    public static final String DEVICETYPE="deviceType";   //手机机型
    public static final String OSVERSION="osVersion";     //系统版本
    public static final String APPVERSION="appVersion";   //app版本
    public static final String SFID="sfId";                //设备标识

    public static final String URL = "http://115.159.58.231:18081";
    //http://115.159.58.231:18081(壕鑫腾讯云)   http://47.100.15.18:8080(阿里云)
    //APP图片拼接url
    public static final String APPPICTERURL="http://115.159.58.231:8888/";
    //http://115.159.58.231:8888/(壕鑫腾讯云)   http://47.100.15.18:8888/(阿里云)
    //壕鑫客服链接
    public static final String SERVICEURL="http://chat10.live800.com/live800/chatClient/chatbox.jsp?companyID=924464&configID=234256&jid=9174122739";
    //getSmsCode
    public static final String GETSMSCODE = URL + "/pooh-web/sms/getRegSMSCode";//"http://controller.ngrok.cc/m/sms/getSMSCodeLogin";
    //login
    public static final String LOGIN = URL + "/pooh-web/sms/getSMSCodeLogin";//"http://controller.ngrok.cc/m/sms/getRegSMSCode";
    //login without code
    public static final String LOGINWITHOUTCODE = URL + "/pooh-web/sms/getDoll";
    //完整的URL：http://106.75.142.42:8080/pooh-web/uploadFiles/DollImage/A002201710310002.jpg
    //娃娃图url
    public static final String PICTUREURL = URL + "/pooh-web/uploadFiles/DollImage/";

    //充值卡url
    public static final String PAYCARDTPURL = URL + "/pooh-web/uploadFiles/paycard/";
    //退出登录
    public static final String LOGOUT=URL+"/pooh-web/app/logout";

    //头像上传http://47.100.15.18:8080/pooh-web/api/updateUser
    public static final String FACEIMAGEURL = URL + "/pooh-web/api/updateUser";

    //修改昵称
    public static final String UserNickNameURL = URL + "/faceImage";

    //修改用户名  11/21 13：10
    public static final String USERNAMEURL = URL + "/pooh-web/api/updateUserName";

    //头像上传成功返回的http://106.75.142.42:8080/faceImage/15335756655.png
    public static final String USERFACEIMAGEURL = APPPICTERURL;//+ "faceImage/";

    //充值接口
    public static final String USERPAYURL = URL + "/pooh-web/pay/balance";

    //消费接口
    public static final String USERPLAYURL = URL + "/pooh-web/pay/costBalance";

    //listRank
    public static final String LISTRANKURL = URL + "/pooh-web/rank/rankList";

    //视屏上传upload
    public static final String UPLOADURL = URL + "/pooh-web/play/regPlayBack";

    //获取视频回放列表
    public static final String VIDEOBACKURL = URL + "/pooh-web/play/getPlayRecord";

    //获取房间用户头像
    public static final String CTRLUSERIMAGE = URL + "/pooh-web/api/getUser";

    //跑马灯l
    public static final String getUserList = URL + "/pooh-web/play/getUserList";

    //下注接口
    public static final String BETSURL = URL + "/pooh-web/app/bets";

    //围观群众分发游戏场次
    public static final String PLAYIDURL = URL + "/pooh-web/app/getPlayId";

    //开始游戏分发场次
    public static final String CREATPLAYLISTURL = URL + "/pooh-web/pay/creatPlayList";

    //获取下注人数对比
    public static final String GETPONDURL=URL + "/pooh-web/app/getPond";

    //发货接口
    public static final String SENDGOODSURL=URL+"/pooh-web/app/sendGoods";

    //收货人信息设置
    public static final String CONSIGNEEURL=URL+"/pooh-web/app/cnsignee";

    //兑换接口
    public static final String EXCHANGEURL=URL+"/pooh-web/app/conversionGoods";

    //兑换列表接口
    public static final String EXCHANGELISTURL=URL+"/pooh-web/app/getConList";

    //获取用户信息接口
    public static final String GETUSERDATEURL=URL+"/pooh-web/api/getUserInfo";    //参数：userId 获取用户信息

    //微信QQ登录创建用户接口
    public static final String YSDKLOGINURL=URL+"/pooh-web/app/tencentLogin";

    //YSDK支付创建订单接口
    public static final String YSDKPAYORDERURL=URL+"/pooh-web/pay/getTradeOrder";

    //YSDK版自动登录
    public static final String YSDKAUTHLOGINURL=URL+"/pooh-web/app/tencentAutoLogin";

    //获取充值卡列表
    public static final String PAYCARDLISTURL=URL+"/pooh-web/pay/getPaycard";

    //用户竞猜记录
//    http://192.168.1.21:8080/pooh-web/app/getGuessDetailTop10
    public static final String GETGUESSDETAIL=URL+"/pooh-web/app/getGuessDetailTop10";

    //金币流水接口
    public static final String CURRENTACCOUNTURL=URL+"/pooh-web/app/getPaymenlist";

    //房间列表接口
    public static final String DOLLLISTURL=URL+"/pooh-web/app/doll/getDollList";

    //签到接口
    public static final String USERSIGNURL=URL+"/pooh-web/app/sign";

    //轮播接口
    public static final String BANNERURL=URL+"/pooh-web/app/getRunImage";

    //排行榜当前用户排名接口
    public static final String RANKLISTURL=URL+"/pooh-web/rank/rankSelfList";

    //物流订单
    public static final String LOGISTICSORDERURL=URL+"/pooh-web/app/getLogistics";

    //分类列表种类
    public static final String GETTOYTYPE = URL + "/pooh-web/app/doll/getAllToyTypeList";

    //分类娃娃机
    public static final String GETTOYSBYTYPE = URL + "/pooh-web/app/doll/getDollPage";

    //查询邀请码
    public static final String USERAWARDCODEURL=URL+"/pooh-web/app/award/getUserAwardCode";

    //兑换邀请码
    public static final String DOAWARDBYUSERCODEURL=URL+"/pooh-web/app/award/doAwardByUserCode";

    //竞猜跑马灯
    public static final String GUESSERLASTTENURL=URL+"/pooh-web/app/getGuesserlast10";

}