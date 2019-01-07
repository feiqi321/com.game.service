package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/27.
 */
public class TaskDTO implements Serializable {

    private int id;

    private int myNum;

    private String openId;

    private String gameId;

    private int taskNum;

    private int  scores;

    private int status;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getMyNum() {
        return myNum;
    }

    public void setMyNum(int myNum) {
        this.myNum = myNum;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }
}
