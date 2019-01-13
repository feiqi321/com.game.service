package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.CollectingDTO;
import com.ovft.configure.sys.bean.DeviceDTO;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
public interface IDeviceService {

    public DeviceDTO findByOpenId(DeviceDTO deviceDTO);

    public DeviceDTO saveOrUpdate(DeviceDTO deviceDTO);

    public void startEvent();

    public int queryEvent();

    public int checkDevice(DeviceDTO deviceDTO);

    public void addScores(DeviceDTO deviceDTO);

    public List<CollectingDTO> collecting(DeviceDTO deviceDTO);

}
