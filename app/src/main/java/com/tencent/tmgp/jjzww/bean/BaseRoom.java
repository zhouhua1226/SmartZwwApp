package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2017/10/31.
 */
public class BaseRoom implements Serializable{
    private String DOLL_URL;
    private String DOLL_NAME;

    public void setDOLL_NAME(String DOLL_NAME) {
        this.DOLL_NAME = DOLL_NAME;
    }

    public String getDOLL_NAME() {
        return DOLL_NAME;
    }

    public void setDOLL_URL(String DOLL_URL) {
        this.DOLL_URL = DOLL_URL;
    }

    public String getDOLL_URL() {
        return DOLL_URL;
    }
}
