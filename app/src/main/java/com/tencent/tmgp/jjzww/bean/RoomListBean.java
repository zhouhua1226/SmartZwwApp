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
    private PageBean pd;

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

    public void setPd(PageBean pd) {
        this.pd = pd;
    }

    public PageBean getPd() {
        return pd;
    }

    public class PageBean {
        private int showCount;
        private String currentType;
        private int totalPage;
        private int currentPage;

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setCurrentType(String currentType) {
            this.currentType = currentType;
        }

        public void setShowCount(int showCount) {
            this.showCount = showCount;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getShowCount() {
            return showCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public String getCurrentType() {
            return currentType;
        }
    }
}
