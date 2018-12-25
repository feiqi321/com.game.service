package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.dao.CollectMapper;
import com.ovft.configure.sys.dao.DeviceColorMapper;
import com.ovft.configure.sys.service.IDeviceColorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class DeviceColorIServiceImpl  implements IDeviceColorService {
    @Resource
    private DeviceColorMapper deviceColorMapper;
    @Resource
    private CollectMapper collectMapper;

    @Override
    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO){
        return deviceColorMapper.findByDeviceId(deviceColorDTO);
    }
    @Override
    public int collect(CollectDTO collectDTO){
        int flag = 0;
        DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
        deviceColorDTO.setDeviceId(collectDTO.getDeviceId());
        DeviceColorDTO resultColor = deviceColorMapper.findByDeviceId(deviceColorDTO);
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        if (!(resultColor.getColor()==resultCollect.getColor1() || resultColor.getColor()==resultCollect.getColor2())){
            flag = 0;
        }else{
            flag = 1;
        }

        return flag;
    }

    @Override
    public int confirm(CollectDTO collectDTO){
        int flag = 1;
        DeviceColorDTO deviceColorDTO = new DeviceColorDTO();
        deviceColorDTO.setDeviceId(collectDTO.getDeviceId());
        DeviceColorDTO resultColor = deviceColorMapper.findByDeviceId(deviceColorDTO);
        CollectDTO resultCollect = collectMapper.findByOpenId(collectDTO);
        if (resultCollect.getColor1()==0){
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.update(resultCollect);
        }else if (resultCollect.getColor1() > 0 && resultCollect.getColor2()==0){
            resultCollect.setColor2(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.update(resultCollect);
        }else if (resultCollect.getColor2() > 0 && resultCollect.getColor3()==0){
            resultCollect.setColor3(resultColor.getColor());
            resultCollect.setStatus(1);
            collectMapper.update(resultCollect);
        }else{
            resultCollect.setColor1(resultColor.getColor());
            resultCollect.setStatus(0);
            collectMapper.save(resultCollect);
        }

        return flag;
    }
    @Override
    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO){
        List<CollectDTO> list = collectMapper.listAllByOpenId(collectDTO);
        return list;
    }

}
