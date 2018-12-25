package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.service.IDeviceColorService;
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
@RequestMapping(value = "/game/deviceColor")
public class DeviceColorController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceColorController.class);
    @Autowired
    private IDeviceColorService iDeviceColorService;

    /**
     *  设备资源颜色查询
     *
     * @return
     */
    @PostMapping(value = "/findByDeviceId")
    public WebResult findByDeviceId(@RequestBody DeviceColorDTO deviceColorDTO)  {
        logger.info("设备资源颜色查询{}",deviceColorDTO.getDeviceId());
        WebResult result = new WebResult();
        try {
            DeviceColorDTO resultDto = iDeviceColorService.findByDeviceId(deviceColorDTO);
            result.setData(resultDto);
            result.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

}
