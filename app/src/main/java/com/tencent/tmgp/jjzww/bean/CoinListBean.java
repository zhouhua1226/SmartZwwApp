package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mi on 2018/5/31.
 */

public class CoinListBean implements Serializable {
    private List<CoinBean> coinPusher;

    public List<CoinBean> getDataList() {
        return coinPusher;
    }

    public void setDataList(List<CoinBean> dataList) {
        this.coinPusher = dataList;
    }

    public class CoinBean {
        private String finishFlag;
        private String costGold;
        private String id;
        private String userId;
        private String returnGold;
        private String roomId;

        public String getFinishFlag() {
            return finishFlag;
        }

        public void setFinishFlag(String finishFlag) {
            this.finishFlag = finishFlag;
        }

        public String getCostGold() {
            return costGold;
        }

        public void setCostGold(String costGold) {
            this.costGold = costGold;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReturnGold() {
            return returnGold;
        }

        public void setReturnGold(String returnGold) {
            this.returnGold = returnGold;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }
}