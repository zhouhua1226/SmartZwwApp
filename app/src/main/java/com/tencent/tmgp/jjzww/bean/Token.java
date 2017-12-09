package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2017/9/8.
 */
public class Token implements Serializable{
    private String accessToken;
    private String expireTime;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getExpireTime() {
        return expireTime;
    }
}
