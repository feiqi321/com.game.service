package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.BossDTO;
import com.ovft.configure.sys.bean.GameDTO;
import com.ovft.configure.sys.dao.BossMapper;
import com.ovft.configure.sys.dao.GameMapper;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.utils.GlobalUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by looyer on 2019/1/6.
 */
@Service
public class GameServiceImpl implements GameService {

    @Resource
    private GameMapper gameMapper;
    @Resource
    private BossMapper bossMapper;



    public void startGame(){
        String gameId = UUID.randomUUID().toString();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(gameId);
        gameMapper.save(gameDTO);
        GlobalUtils.mapCache.put("gameId",gameId);
        BossDTO bossDTO = bossMapper.findBoss();
        GlobalUtils.mapCache.put("blood",bossDTO.getBlood());
        GlobalUtils.mapCache.put("totalBlood",bossDTO.getBlood());
    }

    public void endGame(){
        gameMapper.update();
        GlobalUtils.mapCache.remove("gameId");
    }

}
