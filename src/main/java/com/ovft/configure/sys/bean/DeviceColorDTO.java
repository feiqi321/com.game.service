package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/24.
 */
public class DeviceColorDTO implements Serializable {

    private int id;

    private String deviceId;

    private int color;

    private String url;
    //收集持续时间
    private int continuTime;

    public int getContinuTime() {
        return continuTime;
    }

    public void setContinuTime(int continuTime) {
        this.continuTime = continuTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
