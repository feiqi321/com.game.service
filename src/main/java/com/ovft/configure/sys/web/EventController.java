package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.EventConfigDTO;
import com.ovft.configure.sys.service.IDeviceService;
import com.ovft.configure.sys.service.IEventService;
import com.ovft.configure.utils.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by looyer on 2018/12/24.
 */
@RestController
@RequestMapping(value = "/game/event")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private IDeviceService iDeviceService;
    @Autowired
    private IEventService iEventService;
    /**
     *  世界时间触发
     *
     * @return
     */
    @PostMapping(value = "/startEvent")
    public WebResult startEvent()  {
        logger.info("世界时间触发");
        WebResult result = new WebResult();
        try {
            if (GlobalUtils.event != -1){
                result.setCode("501");
                result.setMsg("世界事件已经开始 ");
                return result;
            }
            iDeviceService.startEvent();
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  世界时间触发
     *
     * @return
     */
    @PostMapping(value = "/stopEvent")
    public WebResult stopEvent()  {
        logger.info("世界时间终止");
        WebResult result = new WebResult();
        try {
            GlobalUtils.event = -1;
            GlobalUtils.mapCache.remove("gameId");
            GlobalUtils.mapCache.remove("gameno");
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  查询当前是那种状态
     *
     * @return
     */
    @PostMapping(value = "/queryEvent")
    public WebResult queryEvent()  {
        logger.info("查询当前是那种状态");
        WebResult result = new WebResult();
        try {
            int event = iDeviceService.queryEvent();
            result.setData(event);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  查询事件对应的时间
     *
     * @return
     */
    @PostMapping(value = "/selectByEvent")
    public WebResult selectByEvent(@RequestBody EventConfigDTO dto)  {
        logger.info("查询事件对应的时间");
        WebResult result = new WebResult();
        try {
            EventConfigDTO eventConfigDTO = iEventService.selectByEvent(dto);
            result.setData(eventConfigDTO);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }


}
