package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.bean.GameDTO;
import com.ovft.configure.sys.bean.Rank;
import com.ovft.configure.sys.bean.TaskDTO;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.sys.service.TaskService;
import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
@RestController
@RequestMapping(value = "/game/game")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    @Autowired
    private GameService gameService;
    @Autowired
    private EventController eventController;

    /**
     *  开始一局新的游戏
     *
     * @return
     */
    @PostMapping(value = "/startGame")
    public WebResult startGame()  {
        logger.info("开始一局新的游戏");
        WebResult result = new WebResult();
        try {
            String gameId = GlobalUtils.mapCache.get("gameId")==null?"":GlobalUtils.mapCache.get("gameId").toString();
            if(StringUtils.isNotEmpty(gameId)){
                result.setCode("500");
                result.setMsg("游戏已经开始");
                return result;
            }
            gameService.startGame();
            eventController.startEvent();
            result.setData("操作成功");
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }

    /**
     *  查询boss的血量
     *
     * @return
     */
    @PostMapping(value = "/findBlood")
    public WebResult findBlood(@RequestBody AttackDTO attackDTO)  {
        logger.info("查询boss的血量");
        WebResult result = new WebResult();
        try {
            attackDTO = gameService.findTotalAttack(attackDTO);

            int blood = GlobalUtils.mapCache.get("blood")==null?0:Integer.parseInt(GlobalUtils.mapCache.get("blood").toString());
            int totalBlood = GlobalUtils.mapCache.get("totalBlood")==null?0:Integer.parseInt(GlobalUtils.mapCache.get("totalBlood").toString());
            BigDecimal percent = null;
            if (blood ==0 || totalBlood == 0){
                percent = new BigDecimal(0);
            }else{
                percent = new BigDecimal(blood).divide(new BigDecimal(totalBlood),0,BigDecimal.ROUND_HALF_DOWN);
            }
            attackDTO.setPercent(percent.intValue());
            result.setData(attackDTO);
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }

    /**
     *  游戏排名
     *
     * @return
     */
    @PostMapping(value = "/gameOrder")
    public WebResult gameOrder()  {
        logger.info("游戏排名");
        WebResult result = new WebResult();
        try {

            result = gameService.listRank();

        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }
}
