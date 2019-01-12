package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.AttackDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2019/1/9.
 */
@Mapper
public interface AttackMapper {

    public void attack(AttackDTO attackDTO);

    public AttackDTO findTotalAttack(AttackDTO attackDTO);

    public List<AttackDTO> findLastThree(AttackDTO attackDTO);

}
