package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.bean.GameDTO;
import com.ovft.configure.sys.bean.Rank;
import com.ovft.configure.sys.bean.TaskDTO;
import com.ovft.configure.sys.service.GameService;
import com.ovft.configure.sys.service.IDeviceService;
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
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    private IDeviceService iDeviceService;

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
            //eventController.startEvent();
            iDeviceService.startEvent();
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
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        try {
            attackDTO = gameService.findTotalAttack(attackDTO);
            if (attackDTO == null){
                attackDTO = new AttackDTO();
            }
            int blood = GlobalUtils.mapCache.get("blood")==null?0:Integer.parseInt(GlobalUtils.mapCache.get("blood").toString());
            int totalBlood = GlobalUtils.mapCache.get("totalBlood")==null?0:Integer.parseInt(GlobalUtils.mapCache.get("totalBlood").toString());
            BigDecimal percent = null;
            if (blood ==0 || totalBlood == 0){
                percent = new BigDecimal(0);
            }else{
                percent = new BigDecimal(blood).divide(new BigDecimal(totalBlood),0,BigDecimal.ROUND_HALF_DOWN);
            }
            attackDTO.setPercent(percent.intValue());
            long timeMi = Long.parseLong(GlobalUtils.mapCache.get("bossTime")==null?"0":GlobalUtils.mapCache.get("bossTime").toString());
            if (timeMi ==0){
                attackDTO.setSed(0);
                attackDTO.setLasttime(0);
            }else {
                long now = new Date().getTime();
                long diff = now - timeMi;
                long min = diff % nd % nh / nm;
                // 计算差多少秒//输出结果
                 long sec = diff % nd % nh % nm / ns;

                int boss = Integer.parseInt(GlobalUtils.mapCache.get("bossLastTime") == null ? "0" : GlobalUtils.mapCache.get("bossLastTime").toString());
                if (min>=boss){
                    attackDTO.setSed(0);
                    attackDTO.setLasttime(0);
                }else {
                    if (sec ==0) {
                        attackDTO.setLasttime((int)(boss - min));
                        attackDTO.setSed((int) sec);
                    }else{
                        attackDTO.setLasttime((int)(boss - min-1));
                        attackDTO.setSed(60-(int) sec);
                    }
                }
            }
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
