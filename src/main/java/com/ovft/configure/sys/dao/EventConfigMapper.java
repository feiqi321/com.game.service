package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.bean.EventConfigDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface EventConfigMapper {

    public EventConfigDTO selectByEvent(EventConfigDTO dto);


}
