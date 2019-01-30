package com.ovft.configure.sys.service.impl;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.*;
import com.ovft.configure.sys.dao.*;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.sys.service.IDeviceService;
import com.ovft.configure.sys.service.WarehouseService;
import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private ExecutorService executor = Executors.newFixedThreadPool(10);
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private DeviceColorMapper deviceColorMapper;
    @Resource
    private GameService gameService;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private RankMapper rankMapper;
    @Resource
    private EventTimeMapper eventTimeMapper;

    @Override
    public DeviceDTO findByOpenId(DeviceDTO deviceDTO){
        DeviceDTO resultDTO = deviceMapper.selectByOpenId(deviceDTO);
        CollectDTO collectDTO = new CollectDTO();
        collectDTO.setGameId(deviceDTO.getGameId());
        collectDTO.setOpenId(deviceDTO.getOpenId());
        int num = collectMapper.findNewNum(collectDTO);
        resultDTO.setNewNum(num);
        DeviceDTO realDTO = new DeviceDTO();
        realDTO.setDeviceId(resultDTO.getDeviceId());
        DeviceDTO realResult = deviceMapper.selectShowDevice(realDTO);
        resultDTO.setUserId(realResult==null?"":realResult.getDeviceId());
        return resultDTO;

    }
    @Override
    public DeviceDTO saveOrUpdate(DeviceDTO deviceDTO){
        DeviceDTO realDevice = deviceMapper.selectRealDevice(deviceDTO);
        if (realDevice == null || StringUtils.isEmpty(realDevice.getDeviceId())){
            return null;
        }
        deviceDTO.setDeviceId(realDevice.getDeviceId());
        DeviceDTO temp = deviceMapper.selectByOpenId(deviceDTO);
        if (temp != null && StringUtils.isNotEmpty(temp.getOpenId())){
            deviceMapper.update(deviceDTO);
        }else{
            deviceMapper.save(deviceDTO);
        }
        return deviceDTO;
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

    public WebResult collecting(DeviceDTO deviceDTO){
        WebResult webResult = new WebResult();
        FirstList firstList = new FirstList();
        List<CollectingDTO> list = new ArrayList<CollectingDTO>();
        List<Boolean> shList = new ArrayList<Boolean>();
        List<Integer> singleList = new ArrayList<Integer>();
        CollectDTO collectDTO = new CollectDTO();
        collectDTO.setOpenId(deviceDTO.getOpenId());
        collectDTO.setGameId(deviceDTO.getGameId());
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        if (resultCollect !=null && resultCollect.getColor1()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor1());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
            if (resultCollect.getPosition1()>0){
                shList.add(true);
                singleList.add(resultCollect.getPosition1());
            }else{
                shList.add(false);
                singleList.add(0);
            }
        }else{
            shList.add(false);
            singleList.add(0);
        }
        if (resultCollect !=null && resultCollect.getColor2()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor2());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
            if (resultCollect.getPosition2()>0){
                shList.add(true);
                singleList.add(resultCollect.getPosition2());
            }else{
                shList.add(false);
                singleList.add(0);
            }
        }else{
            shList.add(false);
            singleList.add(0);
        }
        if (resultCollect !=null && resultCollect.getColor3()!=0){
            CollectingDTO collectingDTO = new CollectingDTO();
            collectingDTO.setType(resultCollect.getColor3());
            collectingDTO.setStatus(1);
            list.add(collectingDTO);
            if (resultCollect.getPosition3()>0){
                shList.add(true);
                singleList.add(resultCollect.getPosition3());
            }else{
                shList.add(false);
                singleList.add(0);
            }
        }else{
            shList.add(false);
            singleList.add(0);
        }
        firstList.setCollectList(list);
        firstList.setShList(shList);
        firstList.setSingleList(singleList);
        webResult.setData(firstList);
        webResult.setCode("200");
        return webResult;
    }


    @Override
    @Async
    public void startEvent(String gameId){
        this.executeAsyncTask(gameId);
    }


    @Override
    public int queryEvent(){
        return GlobalUtils.event;

    }

    public String executeAsyncTask(String gameId){
        try {
            List<EventTimeDTO> eventList = eventTimeMapper.selectByEvent();
            int nomal =0;
            int snow = 0;
            int earth = 0;
            int boss = 0;
            int snowEarth = 0;
            int earthBoss = 0;
            int lastTime = 0;
            for (int i=0;i<eventList.size();i++){
                EventTimeDTO eventTimeDTO = eventList.get(i);
                if (eventTimeDTO.getEvent()==1){
                    nomal = eventTimeDTO.getEventTime();
                }else if (eventTimeDTO.getEvent()==2){
                    snow = eventTimeDTO.getEventTime();
                }else if (eventTimeDTO.getEvent()==3){
                    earth = eventTimeDTO.getEventTime();
                }else if (eventTimeDTO.getEvent()==4){
                    boss = eventTimeDTO.getEventTime();
                    GlobalUtils.mapCache.put("bossLastTime",boss);
                }else if (eventTimeDTO.getEvent()==5){
                    snowEarth = eventTimeDTO.getEventTime();
                }else if (eventTimeDTO.getEvent()==6){
                    earthBoss = eventTimeDTO.getEventTime();
                }else if (eventTimeDTO.getEvent()==7){
                    lastTime = eventTimeDTO.getEventTime();
                }
            }


            GlobalUtils.event = 0;//开始
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            TimeUnit.SECONDS.sleep(nomal*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }
            GlobalUtils.event = 1;//下雪
            EventWebSocket.sendInfo("1");
            GlobalUtils.animationID = 2;
            GlobalUtils.musicID = 2;
            TimeUnit.SECONDS.sleep(snow*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }
            GlobalUtils.event = 0;//恢复
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            EventWebSocket.sendInfo("10");
            TimeUnit.SECONDS.sleep(snowEarth*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }
            GlobalUtils.event = 2;//地震
            GlobalUtils.animationID = 3;
            GlobalUtils.musicID = 3;
            EventWebSocket.sendInfo("2");
            TimeUnit.SECONDS.sleep(earth*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }
            GlobalUtils.event = 0;//恢复
            GlobalUtils.animationID = 1;
            GlobalUtils.musicID = 1;
            EventWebSocket.sendInfo("20");
            TimeUnit.SECONDS.sleep(earthBoss*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }

            GlobalUtils.event = 3;//怪兽袭击
            GlobalUtils.animationID = 4;
            GlobalUtils.musicID = 4;

            GlobalUtils.mapCache.put("bossTime",new Date().getTime());
            EventWebSocket.sendInfo("3");
            this.notice(gameId);
            TimeUnit.SECONDS.sleep(boss*60);
            if (GlobalUtils.event == -1 || GlobalUtils.mapCache.get("gameId")==null || !gameId.equals(GlobalUtils.mapCache.get("gameId"))){
                return null;
            }

            if (GlobalUtils.event<99) {
                GlobalUtils.event = 97;//boss到时间未死亡
                EventWebSocket.sendInfo("97");//boss到时间未死亡
                warehouseService.bossDestroy();
            }
            GlobalUtils.event = 0;//恢复
            EventWebSocket.sendInfo("30");
            TimeUnit.SECONDS.sleep(lastTime*60);
            GlobalUtils.mapCache.remove("gameId");
            GlobalUtils.event = -1;//游戏结束
            GlobalUtils.animationID = 6;
            GlobalUtils.musicID = 6;
            //this.comunicateOrder();
            EventWebSocket.sendInfo("100");//游戏结束

        } catch (Exception e) {
            logger.error(e.getMessage());
            GlobalUtils.mapCache.remove("gameId");
            GlobalUtils.event = -1;
        }finally {
           gameService.endGame();//将游戏的状态设置为1 ，已结束
        }
        return null;
    }

    public void comunicateOrder(){
        String gameId = GlobalUtils.mapCache.get("gameId")==null?"":GlobalUtils.mapCache.get("gameId").toString();
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setGameId(gameId);
        List<DeviceDTO> list = deviceMapper.selectOrder(deviceDTO);
        for (int i=0;i<list.size();i++){
            DeviceDTO temp = list.get(i);
            Rank rank = new Rank();
            rank.setGameId(temp.getGameId());
            rank.setOpenId(temp.getOpenId());
            rank.setNickName(temp.getNickName());
            rank.setImgUrl(temp.getImgUrl());
            rank.setScores(temp.getTotalScores());
            rankMapper.save(rank);
        }

    }

    public void notice(String gameId) throws InterruptedException {
        int total = Integer.parseInt(GlobalUtils.mapCache.get("totalBlood")==null?"0":GlobalUtils.mapCache.get("totalBlood").toString());

        executor.submit(new Runnable() {

            @Override
            public void run() {

                try {
                    for (int i=0 ; i<10000;i++) {
                        TimeUnit.SECONDS.sleep(2);
                        if (GlobalUtils.event ==3 && GlobalUtils.mapCache.get("gameId")!=null && gameId.equals(GlobalUtils.mapCache.get("gameId"))) {
                            int blood = Integer.parseInt(GlobalUtils.mapCache.get("blood") == null ? "0" : GlobalUtils.mapCache.get("blood").toString());
                            if (blood <= 0){
                                break;
                            }
                            BigDecimal percent = new BigDecimal(blood*100).divide(new BigDecimal(total),0,BigDecimal.ROUND_HALF_DOWN);

                            EventWebSocket.sendInfo("98@" + percent.intValue());

                        }else{
                            break;
                        }
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        });

    }


}
