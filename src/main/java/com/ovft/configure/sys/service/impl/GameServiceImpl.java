package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.bean.BossDTO;
import com.ovft.configure.sys.bean.GameDTO;
import com.ovft.configure.sys.bean.Rank;
import com.ovft.configure.sys.dao.AttackMapper;
import com.ovft.configure.sys.dao.BossMapper;
import com.ovft.configure.sys.dao.GameMapper;
import com.ovft.configure.sys.dao.RankMapper;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.utils.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void startGame(){
        String gameId = UUID.randomUUID().toString();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(gameId);
        gameMapper.save(gameDTO);
        GlobalUtils.mapCache.put("gameId",gameId);
        BossDTO bossDTO = bossMapper.findBoss();
        logger.info("游戏开始，boss血量初始化{}",bossDTO.getBlood());
        GlobalUtils.mapCache.put("blood",bossDTO.getBlood());
        GlobalUtils.mapCache.put("totalBlood",bossDTO.getBlood());
        GlobalUtils.mapCache.put("gameno",gameDTO.getId());
    }

    public void endGame(){
        gameMapper.update();
        GlobalUtils.mapCache.remove("gameId");
        GlobalUtils.mapCache.remove("gameno");
    }
    @Override
    public AttackDTO findTotalAttack(AttackDTO attackDTO){
        return attackMapper.findTotalAttack(attackDTO);
    }
    @Override
    public List<Rank> listRank(){
        return rankMapper.findRank();
    }

}
