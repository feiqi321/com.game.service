package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.*;
import com.ovft.configure.sys.dao.*;
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
    @Resource
    private ColorRuleMapper colorRuleMapper;

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
        if (resultCollect!=null && (resultColor.getColor()==resultCollect.getColor1() || resultColor.getColor()==resultCollect.getColor2())){
            result = null;
        }else{
            if (GlobalUtils.event == 1){//下雪
                EventConfigDTO eventConfigDTO = new EventConfigDTO();
                eventConfigDTO.setEvent(2);
                eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                result.setDeviceId(collectDTO.getDeviceId());
                result.setColor(resultColor.getColor());
                result.setUrl(resultColor.getUrl());
                result.setEvent(1);
                result.setContinuTime(eventConfigDTO.getEventTime());

            }else if (GlobalUtils.event == 2){//地震
                result.setEvent(2);
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
                result.setEvent(3);
                EventConfigDTO eventConfigDTO = new EventConfigDTO();
                eventConfigDTO.setEvent(2);
                eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                result.setDeviceId(collectDTO.getDeviceId());
                result.setColor(resultColor.getColor());
                result.setUrl(resultColor.getUrl());
                result.setContinuTime(eventConfigDTO.getEventTime());
            }else{//沒有事件发生
                result.setEvent(0);
                EventConfigDTO eventConfigDTO = new EventConfigDTO();
                eventConfigDTO.setEvent(1);
                eventConfigDTO = eventConfigMapper.selectByEvent(eventConfigDTO);
                result.setDeviceId(collectDTO.getDeviceId());
                result.setColor(resultColor.getColor());
                result.setUrl(resultColor.getUrl());
                result.setContinuTime(eventConfigDTO.getEventTime());
            }
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
        if (resultCollect !=null && resultCollect.getColor1()==0){
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);

            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                deviceDTO.setScores(50);
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(resultCollect.getHands()+1);
            }
            collectMapper.update(resultCollect);
        }else if (resultCollect !=null && resultCollect.getColor1() > 0 && resultCollect.getColor2()==0){
            resultCollect.setColor2(resultColor.getColor());
            resultCollect.setStatus(0);


            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                if (resultCollect.getHands() == 1) {
                    deviceDTO.setScores(100);
                } else {
                    deviceDTO.setScores(50);
                }
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(resultCollect.getHands()+1);
            }
            collectMapper.update(resultCollect);

        }else if (resultCollect !=null && resultCollect.getColor2() > 0 && resultCollect.getColor3()==0){
            resultCollect.setColor3(resultColor.getColor());
            resultCollect.setStatus(1);
            ColorRuleDTO colorRuleDTO = new ColorRuleDTO();
            colorRuleDTO.setColor1(resultCollect.getColor1());
            colorRuleDTO.setColor2(resultCollect.getColor2());
            colorRuleDTO.setColor3(resultColor.getColor());
            colorRuleDTO = colorRuleMapper.findRule(colorRuleDTO);
            if(colorRuleDTO == null){
                flag = 0;
            }else {
                DeviceDTO deviceDTO = new DeviceDTO();
                resultCollect.setScores(colorRuleDTO.getScores());
                resultCollect.setUrl(colorRuleDTO.getUrl());
                resultCollect.setUrl2(colorRuleDTO.getUrl2());

                if (collectDTO.getLength() == 0) {

                    deviceDTO.setOpenId(collectDTO.getOpenId());
                    if (resultCollect.getHands() == 2) {//已经2次手环在
                        deviceDTO.setScores(150);
                    } else if (resultCollect.getHands() == 1) {//已经1次手环在
                        deviceDTO.setScores(100);
                    } else {//已经0次手环在
                        deviceDTO.setScores(50);
                    }
                    resultCollect.setHands(resultCollect.getHands() + 1);
                    deviceDTO.setGameId(collectDTO.getGameId());
                    deviceMapper.addScore(deviceDTO);
                }
                collectMapper.complete(resultCollect);
                deviceDTO.setScores(colorRuleDTO.getScores());
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);//自动领取规则对应的积分
            }
        }else{
            resultCollect = new CollectDTO();
            resultCollect.setOpenId(collectDTO.getOpenId());
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);
            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceDTO.setScores(50);
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(1);
                resultCollect.setViewStatus(2);//沒有完成收集
            }
            collectMapper.save(resultCollect);
        }

        return flag;
    }
    @Override
    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO){
        List<CollectDTO> list = collectMapper.listAllByOpenId(collectDTO);
        return list;
    }

    @Override
    public void view(CollectDTO collectDTO){
        collectMapper.view(collectDTO);
    }

}
