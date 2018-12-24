package com.ovft.configure.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.ovft.configure.http.request.LoginRequest;
import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.service.IConfigureService;
import org.springframework.stereotype.Service;

/**
 * Created by looyer on 2018/10/22.
 */
@Service
public class ConfConfigureServiceImpl implements IConfigureService {


    public WebResult login(LoginRequest loginRequest) {
        WebResult webResult = new WebResult();


        return webResult;
    }

}
