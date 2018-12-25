package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.EventConfigDTO;

/**
 * Created by looyer on 2018/12/24.
 */
public interface IEventService {



    public EventConfigDTO selectByEvent(EventConfigDTO eventConfigDTO);

}
