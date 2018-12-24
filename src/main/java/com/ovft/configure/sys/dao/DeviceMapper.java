package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.DeviceDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface DeviceMapper {

    public DeviceDTO selectByOpenId(DeviceDTO dto);

    public void save(DeviceDTO dto);

    public void update(DeviceDTO dto);

}
