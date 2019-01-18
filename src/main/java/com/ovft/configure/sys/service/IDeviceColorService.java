package com.ovft.configure.sys.service;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.ColorRuleDTO;
import com.ovft.configure.sys.bean.DeviceColorDTO;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
public interface IDeviceColorService {

    public DeviceColorDTO findByDeviceId(DeviceColorDTO deviceColorDTO);

    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO);

    public WebResult collect(CollectDTO collectDTO);

    public WebResult confirm(CollectDTO collectDTO);

    public void view(CollectDTO collectDTO);

}
