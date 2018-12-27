package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.TaskDTO;
import com.ovft.configure.sys.dao.CollectMapper;
import com.ovft.configure.sys.dao.TaskMapper;
import com.ovft.configure.sys.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private CollectMapper collectMapper;

    @Override
    public List<TaskDTO> findByTask(TaskDTO taskDTO){

        List<TaskDTO> resultList = new ArrayList<TaskDTO>();
        List<TaskDTO> list = taskMapper.findByTask(taskDTO);
        for (int i=0;i<list.size();i++){
            TaskDTO temp = list.get(i);
            TaskDTO resultDTO = new TaskDTO();
            BeanUtils.copyProperties(temp,resultDTO);
            if (StringUtils.isNotEmpty(temp.getOpenId())){
                resultDTO.setStatus(1);
            }else{
                resultDTO.setStatus(0);
            }
            CollectDTO collectDTO= new CollectDTO();
            collectDTO.setOpenId(taskDTO.getOpenId());
            List<CollectDTO> myCollect =collectMapper.listAllByOpenId(collectDTO);
            resultDTO.setMyNum(myCollect.size());
            resultList.add(resultDTO);
        }

        return resultList;

    }

}
