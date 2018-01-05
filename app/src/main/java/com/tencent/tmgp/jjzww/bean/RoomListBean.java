package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yincong on 2018/1/5 10:16
 * 修改人：
 * 修改时间：
 * 类描述：房间列表实体类
 */
public class RoomListBean implements Serializable{

    private List<RoomBean> dollList;
    private String code;
    private String msg;

    public List<RoomBean> getDollList() {
        return dollList;
    }

    public void setDollList(List<RoomBean> dollList) {
        this.dollList = dollList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
