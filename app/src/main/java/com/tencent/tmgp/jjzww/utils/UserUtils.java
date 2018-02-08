package com.tencent.tmgp.jjzww.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.gatz.netty.AppClient;
import com.gatz.netty.UserInfo;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.bean.SRStoken;

/**
 * Created by zhouh on 2017/9/19.
 */
public class UserUtils {
    //SP_TAG
    public static final String SP_TAG_LOGIN = "SP_TAG_LOGIN";
    public static final String SP_TAG_USERID="SP_TAG_USERID";
    public static final String SP_TAG_ISLOGOUT="SP_TAG_ISLOGOUT";
    public static final String SP_TAG_ISOPENMUSIC="SP_TAG_ISOPENMUSIC";
    public static final String SP_TAG_PROVINCECITY="SP_TAG_PROVINCECITY";
    public static final String SP_TAG_ISEXCHANGE="SP_TAG_ISEXCHANGE";

    public static String NickName = "";
    public static String UserPhone="";    //用户手机号
    public static String UserName="";    //用户名
    public static String UserImage="";    //用户头像
    public static String UserBalance="";    //用户余额（游戏币）
    public static String UserCatchNum="";   //用户累积抓住次数
    public static String UserAddress="";
    public static String USER_ID="";
    public static String DOLL_ID="";
    public static int UserBetNum;
    public static int id;
    public static SRStoken SRSToken;
    public static boolean isUserChanger = false;  //是否切换

    public static final String RECODE_URL = Environment.getExternalStorageDirectory().getPath()
            + "/SmartRemoteApp/";

    public static void setNettyInfo(String sessionId, String userId, String roomId, boolean isReconnect) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSessionId(sessionId);
        userInfo.setUserId(userId);
        if (isReconnect) {
            userInfo.setRoomid(AppGlobal.getInstance().getUserInfo().getRoomid());
        } else {
            userInfo.setRoomid(roomId);
        }
        userInfo.setUnitId("UNI1611090002765");
        Utils.showLogE("setNettyInfo", "change room::::" + sessionId + "====" + userId + "=====" + userInfo.getRoomid());
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

    public static RoomBean dealWithRoomStatus(RoomBean bean, String stats) {
        if (TextUtils.isEmpty(stats)) {
            bean.setDollState("0");
            return bean;
        }
        int length = bean.getCameras().size();
        if (length < 2) {
            bean.setDollState("0");  //表示当前房间缺失摄像头
        } else {
            String statu1 = bean.getCameras().get(0).getDeviceState();  //第一个摄像头状态 0可以  1不可以
            String statu2 = bean.getCameras().get(1).getDeviceState();  //第二个摄像头状态 0可以  1不可以
            if (stats.equals(Utils.FREE) && statu1.equals("0") && statu2.equals("0")) {
                bean.setDollState("10");
            } else if (stats.equals(Utils.BUSY) && statu1.equals("0") && statu2.equals("0")) {
                bean.setDollState("11");
            } else {
                bean.setDollState("0");  //摄像头状态错误
            }
        }
        return bean;
    }
}
