package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.DeviceDTO;

/**
 * Created by looyer on 2018/12/24.
 */
public interface IDeviceService {

    public DeviceDTO findByOpenId(DeviceDTO deviceDTO);

    public void saveOrUpdate(DeviceDTO deviceDTO);

    public void startEvent();

    public int queryEvent();

}
