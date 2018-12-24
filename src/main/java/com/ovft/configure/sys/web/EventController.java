package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.service.IDeviceService;
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
            iDeviceService.startEvent();
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
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

}
