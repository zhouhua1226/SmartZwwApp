package com.tencent.tmgp.jjzww.utils;

import android.os.Environment;

import com.gatz.netty.AppClient;
import com.gatz.netty.UserInfo;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;

/**
 * Created by zhouh on 2017/9/19.
 */
public class UserUtils {
    //SP_TAG
    public static final String SP_TAG_LOGIN = "SP_TAG_LOGIN";
    public static final String SP_TAG_PHONE = "SP_TAG_PHONE";
    public static final String SP_TAG_USERID="SP_TAG_USERID";
    public static final String SP_TAG_ISLOGOUT="SP_TAG_ISLOGOUT";

    public static String NickName = "";
    public static String UserPhone="";    //用户手机号
    public static String UserName="";    //用户名
    public static String UserImage="";    //用户头像
    public static String UserImage1="";    //用户头像
    public static String UserBalance="";    //用户余额（游戏币）
    public static String UserCatchNum="";   //用户累积抓住次数
    public static String UserAddress="";
    public static String DOllGold="";
    public static String USER_ID="";
    public static String DOLL_ID="";
    public static String GUESSID="";
    public static int id;
    public static int PlayBackId;
    public static boolean isLogout=false;

    public static final String RECODE_URL = Environment.getExternalStorageDirectory().getPath()
            + "/SmartRemoteApp/";
    public static final int RECODE_ERR_CODE_SDCARD_DISABLE = 201001;
    public static final int RECODE_ERR_CODE_SDCARD_FAIL_FOR_MEMORY = 201002;
    public static final int RECODE_ERR_CODE_EZPLAY_NULL = 201003;

    public static void setNettyInfo(String sessionId, String userId, String roomId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSessionId(sessionId);
        userInfo.setUserId(userId);
        userInfo.setRoomid(roomId);
        userInfo.setUnitId("UNI1611090002765");
        Utils.showLogE("setNettyInfo", "change room::::" + sessionId + "====" + userId + "=====" + roomId);
        AppGlobal.getInstance().setUserInfo(userInfo);
    }

    public static void doNettyConnect(final String LogInType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NettyUtils.socketTag) {
                        AppClient.getInstance().doConnect(NickName, LogInType);
                        break;
                    }
                    if (Utils.isExit) {
                        break;
                    }
                }
            }
        }).start();
    }

    public static void doGetDollStatus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NettyUtils.socketTag) {
                        NettyUtils.sendGetDeviceStatesCmd();
                        break;
                    }
                    if (Utils.isExit) {
                        break;
                    }
                }
            }
        }).start();
    }

}
