package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.dao.DeviceMapper;
import com.ovft.configure.sys.service.IDeviceService;
import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public DeviceDTO findByOpenId(DeviceDTO deviceDTO){


        return deviceMapper.selectByOpenId(deviceDTO);

    }
    @Override
    public void saveOrUpdate(DeviceDTO deviceDTO){
        DeviceDTO temp = deviceMapper.selectByOpenId(deviceDTO);
        if (temp != null && StringUtils.isNotEmpty(temp.getDeviceId())){
            deviceMapper.update(deviceDTO);
        }else{
            deviceMapper.save(deviceDTO);
        }

    }
    @Override
    @Async
    public void startEvent(){
        this.executeAsyncTask();
        System.out.println("111");
    }


    @Override
    public int queryEvent(){
        return GlobalUtils.event;

    }

    public void executeAsyncTask(){
        try {
            GlobalUtils.event = 0;//开始
            TimeUnit.MINUTES.sleep(8);
            GlobalUtils.event = 1;//下雪
            TimeUnit.MINUTES.sleep(5);
            GlobalUtils.event = 0;//恢复
            TimeUnit.MINUTES.sleep(8);
            GlobalUtils.event = 2;//恢复
            TimeUnit.MINUTES.sleep(4);
            GlobalUtils.event = 0;//恢复
            TimeUnit.MINUTES.sleep(5);
            GlobalUtils.event = 3;//怪兽袭击
            TimeUnit.MINUTES.sleep(5);
            GlobalUtils.event = 0;//恢复
            TimeUnit.MINUTES.sleep(5);
            GlobalUtils.event = -1;//游戏结束
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            GlobalUtils.event = -1;
        }
    }

}
