package com.ovft.configure.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ManagerMapper
 */
@Mapper
public interface ManagerMapper {
    public String login(@Param("username")String username,@Param("pwd")String pwd);
}