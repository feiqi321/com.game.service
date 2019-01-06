package com.ovft.configure.sys.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
public class WxConf implements Serializable {

    private int id;

    private String wxCode;

    private String appId;

    private String secret;

    private String deviceId;

    private String openId;

    private List<DeviceColorDTO> list;

    public List<DeviceColorDTO> getList() {
        return list;
    }

    public void setList(List<DeviceColorDTO> list) {
        this.list = list;
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

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
