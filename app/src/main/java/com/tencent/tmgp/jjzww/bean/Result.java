package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2017/9/8.
 */
public class Result<T> implements Serializable{
    private T data;
    private String code;
    private String msg;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
