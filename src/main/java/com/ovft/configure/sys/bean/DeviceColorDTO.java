package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/24.
 */
public class DeviceColorDTO implements Serializable {

    private int id;

    private String deviceId;

    private int color;

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
