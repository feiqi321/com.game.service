package com.ovft.configure.http.request;

import com.ovft.configure.sys.bean.PageBean;

/**
 * Created by looyer on 2018/12/4.
 */
public class LoginRequest extends PageBean{

    private int id;

    private String mobile;

    private String fullName;

    private String roleName;

    private String sms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }
}
