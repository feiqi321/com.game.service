package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.dao.AttackMapper;
import com.ovft.configure.sys.service.AttackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by looyer on 2019/1/9.
 */
@Service
public class AttackServiceImpl implements AttackService {

    @Resource
    private AttackMapper attackMapper;

    public void attack(AttackDTO attackDTO){
        attackMapper.attack(attackDTO);
    }

}
