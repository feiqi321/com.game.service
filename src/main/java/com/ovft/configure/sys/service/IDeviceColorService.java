package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.DeviceColorDTO;

/**
 * Created by looyer on 2018/12/24.
 */
public interface IDeviceColorService {

    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO);

}
