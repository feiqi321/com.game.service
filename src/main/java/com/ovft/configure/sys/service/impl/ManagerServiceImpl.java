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
    @Resource
    private BossMapper bossMapper;

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
    public WxConf queryOpenId(String openCode){
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
            DeviceDTO temp = deviceMapper.selectByOpenId(dto);
            wxConf.setOpenId(resultStr);
            logger.info("1111");
            if (temp == null || StringUtils.isEmpty(temp.getOpenId())){
                deviceMapper.save(dto);
                wxConf.setDeviceId("");
            }else{
                wxConf.setDeviceId(temp.getDeviceId());
            }
            logger.info("222");
            List<DeviceColorDTO> list =  deviceColorMapper.findAllColor();
            wxConf.setList(list);

            BossDTO bossDTO = bossMapper.findBoss();
            GlobalUtils.mapCache.put("blood",bossDTO.getBlood());
            GlobalUtils.mapCache.put("totalBlood",bossDTO.getBlood());

        }
        return wxConf;

    }
}