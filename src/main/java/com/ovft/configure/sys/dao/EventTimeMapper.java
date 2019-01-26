package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.EventConfigDTO;
import com.ovft.configure.sys.bean.EventTimeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface EventTimeMapper {

    public List<EventTimeDTO> selectByEvent();


}
