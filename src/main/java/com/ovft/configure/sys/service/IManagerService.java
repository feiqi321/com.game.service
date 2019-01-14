package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.WxConf;

/**
 * IManagerService
 */
public interface IManagerService {
    public boolean login(String username,String pwd);

    /**
     * 获取openId
     * @param openCode
     * @return
     */
    public WxConf queryOpenId(String openCode,String gameId,String nickName,String imgUrl);

    public void saveMonsterHealth(int health);
    
    public int queryMonsterHealth();
}