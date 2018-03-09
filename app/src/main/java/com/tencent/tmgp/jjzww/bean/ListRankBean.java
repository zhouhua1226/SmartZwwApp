package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class ListRankBean implements Serializable {

    private UserBean appUser;
    private List<UserBean> list;

    public UserBean getAppUser() {
        return appUser;
    }

    public void setAppUser(UserBean appUser) {
        this.appUser = appUser;
    }

    public List<UserBean> getList() {
        return list;
    }

    public void setList(List<UserBean> list) {
        this.list = list;
    }



}
