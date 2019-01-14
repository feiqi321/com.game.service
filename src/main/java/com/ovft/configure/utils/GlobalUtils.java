package com.ovft.configure.utils;

import java.util.HashMap;

/**
 * Created by looyer on 2018/12/24.
 */
public class GlobalUtils {

    /**
     * 0：正常
     * 1：下雪
     * 2：地震
     * 3：怪兽
     * -1：结束
     */
    public static int event = -1;

    public static int animationID = 1;

    public static int musicID = 1;

    public static HashMap mapCache = new HashMap();

    public static String getEventName(){
        String eventName = "";
        switch (event) {
            case 0:
                eventName = "正常";
                break;
            case 1:
                eventName = "下雪";
                break;
            case 2:
                eventName = "地震";
                break;
            case 3:
                eventName = "怪兽";
                break;
            case -1:
                eventName = "结束";
                break;
            default:
                eventName = "状态异常";
                break;
        }
        return eventName;
    }
}
