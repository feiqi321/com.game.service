package com.ovft.configure.sys.web;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.WxConf;
import com.ovft.configure.sys.service.IManagerService;

import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ManagerController
 */
@RestController
@RequestMapping("/game/manager")
public class ManagerController {
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private IManagerService iManagerService;

    @RequestMapping("/login")
    public WebResult login(@RequestParam String username,@RequestParam String pwd,HttpSession session){
        WebResult result = new WebResult();
        boolean loginSuccess = iManagerService.login(username, pwd);
        if(loginSuccess){
            Map map = new HashMap();
            String token = UUID.randomUUID().toString();
            session.setAttribute("token", token);
            map.put("token", token);
            result.setCode("200");
            result.setData(map);
        }else{
            result.setCode("501");
            result.setMsg("登陆失败");
        }
        return result;
    }

    /**
     *  第一次进入系统
     *
     * @return
     */
    @PostMapping(value = "/access")
    public WebResult access(@RequestBody WxConf wxConf)  {
        logger.info("第一次进入系统");
        WebResult result = new WebResult();
        try {
            String gameId = GlobalUtils.mapCache.get("gameId")==null?"":GlobalUtils.mapCache.get("gameId").toString();
            if(StringUtils.isEmpty(gameId)){
                result.setCode("500");
                result.setMsg("游戏还未开始");
                return result;
            }
            GlobalUtils.nickCache.put(wxConf.getImgUrl(),wxConf.getNickName());
            WxConf resultDto = iManagerService.queryOpenId(wxConf.getWxCode(),gameId,"",wxConf.getImgUrl());
            if (resultDto==null){
                result.setCode("502");
                result.setMsg("获取微信数据失败");
            }else {
                resultDto.setGameId(gameId);
                result.setData(resultDto);
                result.setCode("200");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    @RequestMapping("/queryGameStatus")
    public WebResult queryGameStatus(@RequestParam String token,HttpSession session){
        WebResult result = new WebResult();
        if(session.getAttribute("token") == null || !session.getAttribute("token").equals(token)){
            result.setCode("501");
            result.setMsg("登陆校验失败");
            return result;
        }
        
        Map map = new HashMap<>();

        int event = GlobalUtils.event;
        map.put("event",event);
        String eventName = GlobalUtils.getEventName();
        map.put("eventName",eventName);

        map.put("health",iManagerService.queryMonsterHealth());
        
        if(GlobalUtils.mapCache.get("gameno") != null){
            int gameno = (int) GlobalUtils.mapCache.get("gameno");
            map.put("gameno",gameno);
        }else{
            map.put("gameno","未知");
        }

        if(GlobalUtils.mapCache.get("gameStartTime") != null){
            Timestamp gameStartTime = (Timestamp) GlobalUtils.mapCache.get("gameStartTime");
            map.put("gameStartTime",gameStartTime.getTime());
        }

        result.setCode("200");
        result.setData(map);
        return result;
    }

    @RequestMapping("/saveMonsterHealth")
    public WebResult saveMonsterHealth(@RequestParam String token,@RequestParam int health,HttpSession session){
        WebResult result = new WebResult();
        if(session.getAttribute("token") == null || !session.getAttribute("token").equals(token)){
            result.setCode("501");
            result.setMsg("登陆校验失败");
            return result;
        }

        if(health > 0){
            iManagerService.saveMonsterHealth(health);
            result.setCode("200");
        }else{
            result.setCode("502");
            result.setMsg("怪兽生命值必须大于0");
        }

        return result;
    }
}