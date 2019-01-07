package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.GameDTO;
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
            result.setData("操作成功");
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }


}