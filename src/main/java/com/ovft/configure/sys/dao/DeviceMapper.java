package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.DeviceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface DeviceMapper {

    public DeviceDTO selectByOpenId(DeviceDTO dto);

    public void save(DeviceDTO dto);

    public void update(DeviceDTO dto);

    public void addScore(DeviceDTO dto);

    public void reduce(DeviceDTO dto);

    public void addBossDestroyScore(DeviceDTO dto);

    public void addDestroyScore(DeviceDTO dto);

    public int checkDevice(DeviceDTO dto);

    public DeviceDTO selectRealDevice(DeviceDTO dto);

    public List<DeviceDTO> selectOrder(DeviceDTO dto);

    public List<DeviceDTO> selectTimeOrder(DeviceDTO dto);

}
