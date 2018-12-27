package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.bean.EventConfigDTO;
import com.ovft.configure.sys.dao.CollectMapper;
import com.ovft.configure.sys.dao.DeviceColorMapper;
import com.ovft.configure.sys.dao.DeviceMapper;
import com.ovft.configure.sys.dao.EventConfigMapper;
import com.ovft.configure.sys.service.IDeviceColorService;
import com.ovft.configure.utils.GlobalUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class DeviceColorIServiceImpl  implements IDeviceColorService {
    @Resource
    private DeviceColorMapper deviceColorMapper;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private EventConfigMapper eventConfigMapper;
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO){
        return deviceColorMapper.findByDeviceId(deviceColorDTO);
    }
    @Override
    public DeviceColorDTO collect(CollectDTO collectDTO){
        DeviceColorDTO result = new DeviceColorDTO();
        DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
        deviceColorDTO.setDeviceId(collectDTO.getDeviceId());
        DeviceColorDTO resultColor = deviceColorMapper.findByDeviceId(deviceColorDTO);
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        //判断还没有收集过此能量
        if (!(resultColor.getColor()==resultCollect.getColor1() || resultColor.getColor()==resultCollect.getColor2())){
            if (GlobalUtils.event == 1){//下雪
                    EventConfigDTO eventConfigDTO = new EventConfigDTO();
                    eventConfigDTO.setEvent(2);
                    eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                    result.setDeviceId(collectDTO.getDeviceId());
                    result.setColor(resultColor.getColor());
                    result.setUrl(resultColor.getUrl());
                    result.setContinuTime(eventConfigDTO.getEventTime());

            }else if (GlobalUtils.event == 2){//地震
                if (resultColor.getColor() == 4){//绿色能量不能收集
                    result = null;
                }else{//在30cm外
                    EventConfigDTO eventConfigDTO = new EventConfigDTO();
                    eventConfigDTO.setEvent(2);
                    eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                    result.setDeviceId(collectDTO.getDeviceId());
                    result.setColor(resultColor.getColor());
                    result.setUrl(resultColor.getUrl());
                    result.setContinuTime(eventConfigDTO.getEventTime());
                }
            }else if (GlobalUtils.event == 3){//怪兽袭击
                    EventConfigDTO eventConfigDTO = new EventConfigDTO();
                    eventConfigDTO.setEvent(2);
                    eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                    result.setDeviceId(collectDTO.getDeviceId());
                    result.setColor(resultColor.getColor());
                    result.setUrl(resultColor.getUrl());
                    result.setContinuTime(eventConfigDTO.getEventTime());
            }else{//沒有事件发生
                    EventConfigDTO eventConfigDTO = new EventConfigDTO();
                    eventConfigDTO.setEvent(1);
                    eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                    result.setDeviceId(collectDTO.getDeviceId());
                    result.setColor(resultColor.getColor());
                    result.setUrl(resultColor.getUrl());
                    result.setContinuTime(eventConfigDTO.getEventTime());
            }
        }else{
            result = null;
        }

        return result;
    }

    @Override
    public int confirm(CollectDTO collectDTO){
        int flag = 1;
        DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
        deviceColorDTO.setDeviceId(collectDTO.getDeviceId());
        DeviceColorDTO resultColor = deviceColorMapper.findByDeviceId(deviceColorDTO);
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        if (resultCollect.getColor1()==0){
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.update(resultCollect);

            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                deviceDTO.setScores(50);
                deviceMapper.addScore(deviceDTO);
            }
        }else if (resultCollect.getColor1() > 0 && resultCollect.getColor2()==0){
            resultCollect.setColor2(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.update(resultCollect);

            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                if (resultCollect.getHands() == 1) {
                    deviceDTO.setScores(100);
                } else {
                    deviceDTO.setScores(50);
                }
                deviceMapper.addScore(deviceDTO);
            }

        }else if (resultCollect.getColor2() > 0 && resultCollect.getColor3()==0){
            resultCollect.setColor3(resultColor.getColor());
            resultCollect.setStatus(1);
            collectMapper.update(resultCollect);
            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                if (resultCollect.getHands() == 2) {
                    deviceDTO.setScores(150);
                } else if(resultCollect.getHands() == 1){
                    deviceDTO.setScores(100);
                }else{
                    deviceDTO.setScores(50);
                }
                deviceMapper.addScore(deviceDTO);
            }
        }else{
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.save(resultCollect);
            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                deviceDTO.setScores(50);
                deviceMapper.addScore(deviceDTO);
            }
        }

        return flag;
    }
    @Override
    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO){
        List<CollectDTO> list = collectMapper.listAllByOpenId(collectDTO);
        return list;
    }

}
