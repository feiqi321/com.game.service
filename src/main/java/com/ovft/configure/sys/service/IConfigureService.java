package com.ovft.configure.sys.service;


import com.ovft.configure.http.request.LoginRequest;
import com.ovft.configure.http.result.WebResult;


/**
 * Created by looyer on 2018/10/22.
 */
public interface IConfigureService {

    /**
     * 登陆
     * @param loginRequest
     * @return
     */
    public WebResult login(LoginRequest loginRequest);

 }
