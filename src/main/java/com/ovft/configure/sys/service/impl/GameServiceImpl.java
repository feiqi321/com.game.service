package com.ovft.configure.sys.service.impl;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.*;
import com.ovft.configure.sys.dao.*;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.sys.service.IDeviceService;
import com.ovft.configure.utils.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by looyer on 2019/1/6.
 */
@Service
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    @Resource
    private GameMapper gameMapper;
    @Resource
    private BossMapper bossMapper;
    @Resource
    private AttackMapper attackMapper;
    @Resource
    private RankMapper rankMapper;
    @Resource
    private DeviceMapper deviceMapper;

    public String startGame(){
        String gameId = UUID.randomUUID().toString();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(gameId);
        gameMapper.save(gameDTO);
        GlobalUtils.mapCache.put("gameId",gameId);
        GlobalUtils.mapCache.put("lastGameIdgameId",gameId);
        BossDTO bossDTO = bossMapper.findBoss();
        logger.info("游戏开始，boss血量初始化{}",bossDTO.getBlood());
        GlobalUtils.mapCache.put("blood",bossDTO.getBlood());
        GlobalUtils.mapCache.put("totalBlood",bossDTO.getBlood());
        GlobalUtils.mapCache.put("gameno",gameDTO.getId());
        GlobalUtils.mapCache.put("gameStartTime",gameMapper.findNewGame().getStartTime());
        //iDeviceService.startEvent();
        return gameId;
    }

    public void endGame(){
        gameMapper.update();
    }
    @Override
    public AttackDTO findTotalAttack(AttackDTO attackDTO){
        return attackMapper.findTotalAttack(attackDTO);
    }
    @Override
    public WebResult listRank(){
        WebResult webResult = new WebResult();
        OrderResponse orderResponse = new OrderResponse();
        GameDTO gameDTO = gameMapper.findLastGame();
        String gameId = gameDTO.getGameId();
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setGameId(gameId);
        List<DeviceDTO> list = deviceMapper.selectOrder(deviceDTO);
        List<Rank> scoreList = new ArrayList<Rank>();
        List<Rank> timeList = new ArrayList<Rank>();
        for (int i=0;i<list.size();i++){
            if (i>=5){
                break;
            }
            DeviceDTO temp = list.get(i);
            Rank rank = new Rank();
            rank.setGameId(temp.getGameId());
            rank.setOpenId(temp.getOpenId());
            rank.setNickName(GlobalUtils.nickCache.get(temp.getImgUrl())==null?"":GlobalUtils.nickCache.get(temp.getImgUrl()).toString());
            rank.setImgUrl(temp.getImgUrl());
            rank.setScores(temp.getTotalScores());
            scoreList.add(rank);
        }
        List<DeviceDTO> timeDevicelist = deviceMapper.selectTimeOrder(deviceDTO);
        for (int i=0;i<timeDevicelist.size();i++){
            if (i>=23){
                break;
            }
            DeviceDTO temp = timeDevicelist.get(i);
            Rank rank = new Rank();
            rank.setGameId(temp.getGameId());
            rank.setOpenId(temp.getOpenId());
            rank.setNickName(temp.getNickName());
            rank.setImgUrl(temp.getImgUrl());
            rank.setScores(temp.getTotalScores());
            rank.setCreateTime(temp.getCreateTime());
            timeList.add(rank);
        }
        orderResponse.setScoreList(scoreList);
        orderResponse.setTimeList(timeList);
        orderResponse.setEvent(GlobalUtils.event);
        webResult.setData(orderResponse);
        webResult.setCode("200");
        return webResult;
    }

}
