package com.ovft.configure.sys.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.service.IManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ManagerController
 */
@RestController
@RequestMapping("/game/manager")
public class ManagerController {
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
    
}