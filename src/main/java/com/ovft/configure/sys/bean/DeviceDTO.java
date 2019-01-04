package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/24.
 */
public class DeviceDTO implements Serializable {

    private int id;

    private String openId;

    private String deviceId;

    private int scores;

    private int type;//1蓝牙资源  2手环

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
