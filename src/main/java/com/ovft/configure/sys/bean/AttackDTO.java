package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2019/1/9.
 */
public class AttackDTO implements Serializable {

    private int id;

    private String openId;

    private String gameId;

    private int attack;

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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
