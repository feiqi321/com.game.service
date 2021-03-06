package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.DeviceColorDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface DeviceColorMapper {

    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO);

    public List<DeviceColorDTO> findAllColor();
}
