package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.dao.DeviceMapper;
import com.ovft.configure.sys.service.IDeviceService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * Created by looyer on 2018/12/24.
 */
public class DeviceServiceImpl implements IDeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public DeviceDTO findByOpenId(DeviceDTO deviceDTO){


        return deviceMapper.selectByOpenId(deviceDTO);

    }
    @Override
    public void saveOrUpdate(DeviceDTO deviceDTO){
        DeviceDTO temp = deviceMapper.selectByOpenId(deviceDTO);
        if (temp != null && StringUtils.isNotEmpty(temp.getDeviceId())){
            deviceMapper.update(deviceDTO);
        }else{
            deviceMapper.save(deviceDTO);
        }

    }

}
