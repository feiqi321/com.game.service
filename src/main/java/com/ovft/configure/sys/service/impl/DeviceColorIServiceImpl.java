package com.ovft.configure.sys.service.impl;

import com.ovft.configure.http.result.WebResult;
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
    public WebResult collect(CollectDTO collectDTO){
        WebResult webResult = new WebResult();
        DeviceColorDTO result = new DeviceColorDTO();
        DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
        deviceColorDTO.setDeviceId(collectDTO.getDeviceId());
        DeviceColorDTO resultColor = deviceColorMapper.findByDeviceId(deviceColorDTO);
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        //判断还没有收集过此能量
        if (resultCollect!=null && (resultColor.getColor()==resultCollect.getColor1() || resultColor.getColor()==resultCollect.getColor2())){
            webResult.setCode("500");
            webResult.setMsg("同一组合不能收集同种能量");
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
                    webResult.setMsg("地震阶段,不允许收集绿色能量");
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
        webResult.setCode("200");
        webResult.setData(result);
        return webResult;
    }

    @Override
    public WebResult confirm(CollectDTO collectDTO){
        WebResult result = new WebResult();
        int singleReward=0;
        int groupReward=0;
        int totalReward=0;
        String bigUrl = "";
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
                deviceDTO.setScores(5);
                singleReward = 5;
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(resultCollect.getHands()+1);
            }
            resultCollect.setGameId(collectDTO.getGameId());
            collectMapper.update(resultCollect);
        }else if (resultCollect !=null && resultCollect.getColor1() > 0 && resultCollect.getColor2()==0){
            resultCollect.setColor2(resultColor.getColor());
            resultCollect.setStatus(0);
            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                if (resultCollect.getHands() == 1) {
                    deviceDTO.setScores(10);
                    singleReward = 10;
                } else {
                    deviceDTO.setScores(5);
                    singleReward = 5;
                }
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(resultCollect.getHands()+1);
            }
            resultCollect.setGameId(collectDTO.getGameId());
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
                result.setCode("502");
                result.setMsg("收集失败");
                return result;
            }else {
                DeviceDTO deviceDTO = new DeviceDTO();
                resultCollect.setScores(colorRuleDTO.getScores());
                resultCollect.setUrl(colorRuleDTO.getUrl());
                resultCollect.setUrl2(colorRuleDTO.getUrl2());
                deviceDTO.setOpenId(collectDTO.getOpenId());
                if (collectDTO.getLength() == 0) {

                    if (resultCollect.getHands() == 2) {//已经2次手环在
                        deviceDTO.setScores(15);
                        singleReward = 15;
                        groupReward = colorRuleDTO.getScores();
                        totalReward = 30;
                        bigUrl = colorRuleDTO.getUrl2();
                    } else if (resultCollect.getHands() == 1) {//已经1次手环在
                        deviceDTO.setScores(10);
                        totalReward = 15;
                        singleReward = 10;
                    } else {//已经0次手环在
                        deviceDTO.setScores(5);
                        totalReward = 5;
                        singleReward = 5;
                    }
                    resultCollect.setHands(resultCollect.getHands() + 1);
                    deviceDTO.setGameId(collectDTO.getGameId());
                    deviceMapper.addScore(deviceDTO);
                }
                resultCollect.setGameId(collectDTO.getGameId());
                collectMapper.complete(resultCollect);
                deviceDTO.setScores(colorRuleDTO.getScores());
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceMapper.addScore(deviceDTO);//自动领取规则对应的积分
            }
        }else{
            resultCollect = new CollectDTO();
            resultCollect.setOpenId(collectDTO.getOpenId());
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setGameId(collectDTO.getGameId());
            resultCollect.setStatus(0);
            if (collectDTO.getLength()==0) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setOpenId(collectDTO.getOpenId());
                deviceDTO.setGameId(collectDTO.getGameId());
                deviceDTO.setScores(5);
                deviceMapper.addScore(deviceDTO);
                resultCollect.setHands(1);
                resultCollect.setViewStatus(2);//沒有完成收集
            }
            collectMapper.save(resultCollect);
        }
        DeviceDTO resultQuery = new DeviceDTO();
        resultQuery.setOpenId(collectDTO.getOpenId());
        resultQuery.setGameId(collectDTO.getGameId());
        DeviceDTO resultDevice = deviceMapper.selectByOpenId(resultQuery);
        resultDevice.setBigUrl(bigUrl);
        resultDevice.setSingleReward(singleReward);
        resultDevice.setGroupReward(groupReward);
        resultDevice.setTotalReward(totalReward);
        result.setCode("200");
        result.setData(resultDevice);
        return result;
    }
    @Override
    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO){
        List<CollectDTO> list = collectMapper.listAllByOpenId(collectDTO);
        collectMapper.view(collectDTO);
        return list;
    }

    @Override
    public void view(CollectDTO collectDTO){
        collectMapper.view(collectDTO);
    }

}
