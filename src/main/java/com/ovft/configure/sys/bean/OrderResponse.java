package com.ovft.configure.sys.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by looyer on 2019/1/17.
 */
public class OrderResponse implements Serializable {

    private int event;

    private List<Rank> scoreList;

    private List<Rank> timeList;

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public List<Rank> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Rank> scoreList) {
        this.scoreList = scoreList;
    }

    public List<Rank> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Rank> timeList) {
        this.timeList = timeList;
    }
}
