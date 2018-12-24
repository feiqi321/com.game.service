package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.service.IDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public WebResult api(@RequestBody DeviceDTO deviceDTO)  {
        logger.info("手环绑定查询{}",deviceDTO.getOpenId());
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


}
