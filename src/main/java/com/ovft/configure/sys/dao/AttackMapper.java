package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.AttackDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2019/1/9.
 */
@Mapper
public interface AttackMapper {

    public void attack(AttackDTO attackDTO);

}
