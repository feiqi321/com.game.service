package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/24.
 */
public class DeviceDTO implements Serializable {

    private int id;

    private String openId;

    private String gameId;

    private String deviceId;

    private int scores;

    private int totalScores;

    private int type;//1蓝牙资源  2手环

    private String nickName;

    private String imgUrl;

    private int singleReward;

    private int groupReward;

    private int totalReward;

    private String bigUrl;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public int getSingleReward() {
        return singleReward;
    }

    public void setSingleReward(int singleReward) {
        this.singleReward = singleReward;
    }

    public int getGroupReward() {
        return groupReward;
    }

    public void setGroupReward(int groupReward) {
        this.groupReward = groupReward;
    }

    public int getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(int totalReward) {
        this.totalReward = totalReward;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(int totalScores) {
        this.totalScores = totalScores;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
