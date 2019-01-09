package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2019/1/9.
 */
public class CollectingDTO implements Serializable {

    private int type;

    private int status;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
