package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.CollectingDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.service.IDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@RestController
@RequestMapping(value = "/game/device")
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private IDeviceService iDeviceService;

    /**
     *  手环设备绑定查询
     *
     * @return
     */
    @PostMapping(value = "/query")
    public WebResult query(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("手环绑定查询{}-{}",deviceDTO.getOpenId(),deviceDTO.getGameId());
        WebResult result = new WebResult();
        try {
            DeviceDTO resultDto = iDeviceService.findByOpenId(deviceDTO);
            result.setData(resultDto);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  手环设备绑定
     *
     * @return
     */
    @PostMapping(value = "/bind")
    public WebResult bind(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("手环绑定{}-{}",deviceDTO.getOpenId(),deviceDTO.getDeviceId());
        WebResult result = new WebResult();
        try {
            iDeviceService.saveOrUpdate(deviceDTO);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  领取奖励
     *
     * @return
     */
    @PostMapping(value = "/addScores")
    public WebResult addScores(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("领取奖励{}-{}",deviceDTO.getOpenId(),deviceDTO.getScores());
        WebResult result = new WebResult();
        try {
            iDeviceService.addScores(deviceDTO);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  判断是否是资源或者是自己的手环
     *
     * @return
     */
    @PostMapping(value = "/checkDevice")
    public WebResult checkDevice(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("判断是否是资源或者是自己的手环{}-{}",deviceDTO.getOpenId(),deviceDTO.getDeviceId());
        WebResult result = new WebResult();
        try {
            int flag = iDeviceService.checkDevice(deviceDTO);
            if (flag == 1) {
                result.setCode("200");
            }else{
                result.setCode("506");
                result.setMsg("非指定资源或手环");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  查询正在收集中的颜色资源
     *
     * @return
     */
    @PostMapping(value = "/collecting")
    public WebResult collecting(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("判断是否是资源或者是自己的手环{}-{}",deviceDTO.getOpenId(),deviceDTO.getGameId());
        WebResult result = new WebResult();
        try {
            List<CollectingDTO> list = iDeviceService.collecting(deviceDTO);
            result.setCode("200");
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

}
