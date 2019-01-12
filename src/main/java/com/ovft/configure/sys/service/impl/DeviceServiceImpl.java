package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.CollectingDTO;
import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.dao.CollectMapper;
import com.ovft.configure.sys.dao.DeviceColorMapper;
import com.ovft.configure.sys.dao.DeviceMapper;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.sys.service.IDeviceService;
import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private DeviceColorMapper deviceColorMapper;
    @Resource
    private GameService gameService;
    @Resource
    private CollectMapper collectMapper;

    @Override
    public DeviceDTO findByOpenId(DeviceDTO deviceDTO){


        return deviceMapper.selectByOpenId(deviceDTO);

    }
    @Override
    public void saveOrUpdate(DeviceDTO deviceDTO){
        DeviceDTO temp = deviceMapper.selectByOpenId(deviceDTO);
        if (temp != null && StringUtils.isNotEmpty(temp.getOpenId())){
            deviceMapper.update(deviceDTO);
        }else{
            deviceMapper.save(deviceDTO);
        }

    }

    @Override
    public void addScores(DeviceDTO deviceDTO){
        deviceMapper.addScore(deviceDTO);
    }

    @Override
    public int checkDevice(DeviceDTO deviceDTO){
        int flag = 0;//不是蓝牙资源或者手环
        if (deviceDTO.getType()==1){////1蓝牙资源  2手环
            DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
            deviceColorDTO.setDeviceId(deviceDTO.getDeviceId());
            DeviceColorDTO temp = deviceColorMapper.findByDeviceId(deviceColorDTO);
            if (temp ==null || temp.getDeviceId()==null || temp.getDeviceId().equals("")){
                flag = 0;
            }else {
                flag = 1; //是蓝牙资源
            }
        }else if (deviceDTO.getType()==2){
            flag = deviceMapper.checkDevice(deviceDTO);
        }

        return flag;
    }

    public List<CollectingDTO> collecting(DeviceDTO deviceDTO){
        List<CollectingDTO> list = new ArrayList<CollectingDTO>();
        CollectDTO collectDTO = new CollectDTO();
        collectDTO.setOpenId(deviceDTO.getOpenId());
        collectDTO.setGameId(deviceDTO.getGameId());
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        if (resultCollect !=null && resultCollect.getColor1()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor1());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
        }
        if (resultCollect !=null && resultCollect.getColor2()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor2());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
        }
        if (resultCollect !=null && resultCollect.getColor3()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor3());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
        }
        return list;
    }

    @Override
    @Async
    public void startEvent(){
        this.executeAsyncTask();
    }


    @Override
    public int queryEvent(){
        return GlobalUtils.event;

    }

    public String executeAsyncTask(){
        try {
            GlobalUtils.event = 0;//开始
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 1;//下雪
            EventWebSocket.sendInfo("1");
            GlobalUtils.animationID = 2;
            GlobalUtils.musicID = 2;
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 0;//恢复
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            EventWebSocket.sendInfo("10");
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 2;//地震
            GlobalUtils.animationID = 3;
            GlobalUtils.musicID = 3;
            EventWebSocket.sendInfo("2");
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 0;//恢复
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            EventWebSocket.sendInfo("20");
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 3;//怪兽袭击
            GlobalUtils.animationID = 4;
            GlobalUtils.musicID = 4;
            EventWebSocket.sendInfo("3");
            this.notice();
            TimeUnit.MINUTES.sleep(1);
            if (GlobalUtils.event == -1){
                return null;
            }
            GlobalUtils.event = 0;//恢复
            if (GlobalUtils.event<=99) {
                EventWebSocket.sendInfo("97");//boss到时间未死亡
            }
            TimeUnit.MINUTES.sleep(1);
            GlobalUtils.event = -1;//游戏结束
            GlobalUtils.animationID = 6;
            GlobalUtils.musicID = 6;
            //gameService.endGame();//将游戏的状态设置为1 ，已结束
        } catch (Exception e) {
            logger.error(e.getMessage());
            GlobalUtils.event = -1;
        }
        return null;
    }

    @Async
    public void notice(){
        int total = Integer.parseInt(GlobalUtils.mapCache.get("totalBlood")==null?"0":GlobalUtils.mapCache.get("totalBlood").toString());

        try {
            for (int i=0 ; i<100;i++) {
                if (GlobalUtils.event != -1 && GlobalUtils.event != 99) {
                    TimeUnit.SECONDS.sleep(2);
                    int blood = Integer.parseInt(GlobalUtils.mapCache.get("blood") == null ? "0" : GlobalUtils.mapCache.get("blood").toString());
                    if (blood <= 0){
                        break;
                    }
                    BigDecimal percent = new BigDecimal(blood*100).divide(new BigDecimal(total),0,BigDecimal.ROUND_HALF_DOWN);

                    EventWebSocket.sendInfo("98@" + percent);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            GlobalUtils.event = -1;
        }
    }

}
