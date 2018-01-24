package com.tencent.tmgp.jjzww.utils;

import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;

/**
 * Created by yincong on 2017/12/13 11:49
 * 修改人：
 * 修改时间：
 * 类描述：YSDK回调信息储存类
 */
public class YsdkUtils {

    public static String uid = "";            //用户唯一标识id，支付时会用到
    public static String access_token = "";  //用户支付通行token，支付时会用到
    public static String auth_token= "";     //用户身份token
    public static String nickName="";        //用户昵称
    public static String imageUrl="";        //用户头像(只有微信或QQ登录，并且用户有头像时才会有效)
    public static final String AUTH_TOKEN="AUTH_TOKEN";
    public static boolean isShare=false;
    public static Result<HttpDataInfo> loginResult;    //登录信息返回类


}
