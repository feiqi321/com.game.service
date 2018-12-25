package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2018/12/24.
 */
public class EventConfigDTO implements Serializable {

    private int event;

    private int eventTime;

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getEventTime() {
        return eventTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }
}
