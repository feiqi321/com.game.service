package com.ovft.configure.sys.service.impl;

import javax.annotation.Resource;

import com.ovft.configure.sys.dao.ManagerMapper;
import com.ovft.configure.sys.service.IManagerService;

import org.springframework.stereotype.Service;

/**
 * ManagerServiceImpl
 */
@Service
public class ManagerServiceImpl implements IManagerService{
    @Resource
    private ManagerMapper managerMapper;

    @Override
    public boolean login(String username, String pwd) {
        String result = managerMapper.login(username, pwd);
        return "1".equals(result);
    }
}