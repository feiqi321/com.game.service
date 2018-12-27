package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.CollectDTO;
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

import java.util.List;

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

    /**
     *  收集资源判断
     *
     * @return
     */
    @PostMapping(value = "/collect")
    public WebResult collect(@RequestBody CollectDTO collectDTO)  {
        logger.info("收集资源判断{}{}",collectDTO.getDeviceId(),collectDTO.getOpenId());
        WebResult result = new WebResult();
        try {
            DeviceColorDTO deviceColorDTO = iDeviceColorService.collect(collectDTO);
            if (deviceColorDTO == null) {
                result.setCode("502");
                result.setMsg("不能收集此能量");
            }else{
                result.setCode("200");
                result.setData(deviceColorDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  收集资源确认
     *
     * @return
     */
    @PostMapping(value = "/confirm")
    public WebResult confirm(@RequestBody CollectDTO collectDTO)  {
        logger.info("收集资源确认{}{}",collectDTO.getDeviceId(),collectDTO.getOpenId());
        WebResult result = new WebResult();
        try {
            int flag = iDeviceColorService.confirm(collectDTO);
            if (flag==1) {
                result.setCode("200");
            }else{
                result.setCode("502");
                result.setMsg("收集失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  收集资源确认
     *
     * @return
     */
    @PostMapping(value = "/listAllByOpenId")
    public WebResult listAllByOpenId(@RequestBody CollectDTO collectDTO)  {
        logger.info("展示个人收集的信息{}",collectDTO.getOpenId());
        WebResult result = new WebResult();
        try {
            List<CollectDTO> list = iDeviceColorService.listAllByOpenId(collectDTO);
            result.setCode("200");
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

}
