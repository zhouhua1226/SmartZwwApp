package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/11/22 11:36
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class AppUserBean implements Serializable {

    private UserBean appUser;

    public UserBean getAppUser() {
        return appUser;
    }

    public void setAppUser(UserBean appUser) {
        this.appUser = appUser;
    }

}
