package com.ovft.configure.sys.web;

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
    public WebResult startEvent(@RequestBody WxConf wxConf)  {
        logger.info("第一次进入系统");
        WebResult result = new WebResult();
        try {
            String gameId = GlobalUtils.mapCache.get("gameId")==null?"":GlobalUtils.mapCache.get("gameId").toString();
            if(StringUtils.isEmpty(gameId)){
                result.setCode("500");
                result.setMsg("游戏还未开始");
                return result;
            }
            WxConf resultDto = iManagerService.queryOpenId(wxConf.getWxCode(),gameId);
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

}