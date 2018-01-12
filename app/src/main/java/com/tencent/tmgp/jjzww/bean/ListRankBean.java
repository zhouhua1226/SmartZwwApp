package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class ListRankBean implements Serializable {

    private List<UserBean> appUser;

    public List<UserBean> getAppUser() {
        return appUser;
    }

    public void setAppUser(List<UserBean> appUser) {
        this.appUser = appUser;
    }



}
