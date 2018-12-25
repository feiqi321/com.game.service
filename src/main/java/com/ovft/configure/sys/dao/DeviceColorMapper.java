package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.DeviceColorDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface DeviceColorMapper {

    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO);

}
