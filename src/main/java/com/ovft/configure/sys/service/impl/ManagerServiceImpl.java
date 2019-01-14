package com.ovft.configure.sys.service.impl;

import javax.annotation.Resource;

import com.ovfintech.common.json.JsonUtil;
import com.ovft.configure.sys.bean.BossDTO;
import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.bean.WxConf;
import com.ovft.configure.sys.dao.*;
import com.ovft.configure.sys.service.IManagerService;

import com.ovft.configure.utils.GlobalUtils;
import com.ovft.configure.utils.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * ManagerServiceImpl
 */
@Service
public class ManagerServiceImpl implements IManagerService{
    private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private WxConfMapper wxConfMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private DeviceColorMapper deviceColorMapper;

    @Override
    public boolean login(String username, String pwd) {
        String result = managerMapper.login(username, pwd);
        return "1".equals(result);
    }

    /**
     * 获取openId
     * @param openCode
     * @return
     */
    public WxConf queryOpenId(String openCode,String gameId,String nickName,String imgUrl){
        WxConf wxConf = wxConfMapper.findWxConf();
        logger.info("https://api.weixin.qq.com/sns/jscode2session?appid="+wxConf.getAppId()+"&secret="+wxConf.getSecret()+"&js_code="+openCode+"&grant_type=authorization_code");
        String json = HttpClient.httpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+wxConf.getAppId()+"&secret="+wxConf.getSecret()+"&js_code="+openCode+"&grant_type=authorization_code");
        logger.info(json);
        @SuppressWarnings("unchecked")
        HashMap<String,String> result = JsonUtil.toBean(json, HashMap.class);
        if (result.get("openid")==null || result.get("openid").equals("")){
            wxConf = null;
        }else{
            String resultStr = result.get("openid");
            logger.info("resultStr:"+resultStr);
            DeviceDTO dto = new DeviceDTO();
            dto.setOpenId(resultStr);
            dto.setGameId(gameId);
            DeviceDTO temp = deviceMapper.selectByOpenId(dto);
            wxConf.setOpenId(resultStr);
            if (temp == null || StringUtils.isEmpty(temp.getOpenId())){
                dto.setScores(0);
                dto.setNickName(nickName);
                dto.setImgUrl(imgUrl);
                deviceMapper.save(dto);
                wxConf.setDeviceId("");
            }else{
                wxConf.setDeviceId(temp.getDeviceId());
            }
            logger.info("222");
            List<DeviceColorDTO> list =  deviceColorMapper.findAllColor();
            wxConf.setList(list);



        }
        return wxConf;

    }
}