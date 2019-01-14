package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.BossDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by looyer on 2019/1/9.
 */
@Mapper
public interface BossMapper {

    public BossDTO findBoss();

    public void updateHealth(@Param("health")int health);
}
