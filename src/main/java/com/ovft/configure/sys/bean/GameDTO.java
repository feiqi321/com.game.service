package com.ovft.configure.sys.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by looyer on 2019/1/6.
 */
public class GameDTO implements Serializable {

    private int id;

    private String gameId;

    private int status;

    private Timestamp startTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * @return the startTime
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}
