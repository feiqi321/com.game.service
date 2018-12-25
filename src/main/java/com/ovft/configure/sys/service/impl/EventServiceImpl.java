package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.EventConfigDTO;
import com.ovft.configure.sys.dao.EventConfigMapper;
import com.ovft.configure.sys.service.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by looyer on 2018/12/24.
 */
@Service
public class EventServiceImpl implements IEventService{

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    @Resource
    private EventConfigMapper eventConfigMapper;

    public EventConfigDTO selectByEvent(EventConfigDTO eventConfigDTO){
        return eventConfigMapper.selectByEvent(eventConfigDTO);
    }

}
