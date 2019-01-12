package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.dao.AttackMapper;
import com.ovft.configure.sys.service.AttackService;
import com.ovft.configure.utils.GlobalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by looyer on 2018/12/24.
 */
@ServerEndpoint(value = "/websocket")
@Component
public class EventWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<EventWebSocket> webSocketSet = new CopyOnWriteArraySet<EventWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage(GlobalUtils.event+"");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        if (GlobalUtils.event == 3) {
            String[] userinfo = message.split(",");
            String openId = userinfo[0];
            String gameId = userinfo[1];
            String attack = userinfo[2];
            AttackDTO attackDTO = new AttackDTO();
            attackDTO.setOpenId(openId);
            attackDTO.setGameId(gameId);
            attackDTO.setAttack(Integer.parseInt(attack));
            this.attack(attackDTO);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        GlobalUtils.event = -1;
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (EventWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        EventWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        EventWebSocket.onlineCount--;
    }

    public synchronized void attack(AttackDTO attackDTO){
        int blood = Integer.parseInt(GlobalUtils.mapCache.get("blood")==null?"0":GlobalUtils.mapCache.get("blood").toString());
        AttackService attackService = SpringUtils.getBean(AttackServiceImpl.class);

        if (blood>0 && blood>attackDTO.getAttack()) {
            GlobalUtils.mapCache.put("blood",blood-attackDTO.getAttack());
            attackService.attack(attackDTO);
        }else if (blood>0 && blood<=attackDTO.getAttack()){
            GlobalUtils.mapCache.put("blood",0);
            attackService.attack(attackDTO);
            GlobalUtils.animationID = 5;
            GlobalUtils.musicID = 5;
            for (EventWebSocket item : webSocketSet) {
                try {
                    item.sendMessage("99");
                    GlobalUtils.event = 99;//boss死亡
                } catch (IOException e) {
                    continue;
                }
            }
        }else{
            GlobalUtils.mapCache.put("blood",0);
        }
    }

}
