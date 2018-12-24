package com.ovft.configure.sys.bean;

/**
 * Created by looyer on 2018/10/26.
 */
public class BaseDTO {

    private String code;

    private String msg;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
