package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2019/1/9.
 */
public class BossDTO implements Serializable {

    private int id;

    private int blood;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }
}
