package com.ovft.configure.sys.service.impl;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.CollectDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.bean.TaskDTO;
import com.ovft.configure.sys.dao.CollectMapper;
import com.ovft.configure.sys.dao.DeviceMapper;
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
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public TaskDTO findByTask(TaskDTO taskDTO){

        TaskDTO resultDTO = new TaskDTO();
        BeanUtils.copyProperties(taskDTO,resultDTO);
        List<TaskDTO> list = taskMapper.findByTask(taskDTO);
        for (int i=0;i<list.size();i++){
            TaskDTO temp = list.get(i);

            if (temp.getType()==1 && StringUtils.isNotEmpty(temp.getOpenId())){
                resultDTO.setFirstNum(temp.getTaskNum());
                resultDTO.setStatus(1);//小任务已经领了奖励积分
                resultDTO.setScores(temp.getScores());
            }else if (temp.getType()==1 && StringUtils.isEmpty(temp.getOpenId())){
                resultDTO.setFirstNum(temp.getTaskNum());
                resultDTO.setStatus(0);//小任务还没领奖励积分
                resultDTO.setScores(temp.getScores());
            }else if (temp.getType()==2 && StringUtils.isNotEmpty(temp.getOpenId())){
                resultDTO.setTotalNum(temp.getTaskNum());
                resultDTO.setTotalStatus(1);//总任务已经领奖励积分
                resultDTO.setTotalScores(temp.getScores());
            }else if (temp.getType()==2 && StringUtils.isEmpty(temp.getOpenId())){
                resultDTO.setTotalNum(temp.getTaskNum());
                resultDTO.setTotalStatus(0);//总任务还没领奖励积分
                resultDTO.setTotalScores(temp.getScores());
            }else{
                resultDTO.setTotalStatus(0);
                resultDTO.setStatus(0);
            }

        }
        CollectDTO collectDTO= new CollectDTO();
        collectDTO.setOpenId(taskDTO.getOpenId());
        collectDTO.setGameId(taskDTO.getGameId());
        List<CollectDTO> myCollect =collectMapper.listAllOrderByOpenId(collectDTO);
        resultDTO.setMyNum(myCollect.size());
        if (resultDTO.getMyNum()<resultDTO.getFirstNum()){
            resultDTO.setStatus(3);//未达到领取条件
        }
        if (resultDTO.getMyNum()<resultDTO.getTotalNum()){
            resultDTO.setTotalStatus(3);//未达到领取条件
        }
        return resultDTO;

    }

    @Override
    public WebResult reward(TaskDTO taskDTO){
        WebResult webResult = new WebResult();
        TaskDTO queryReward = taskMapper.getReward(taskDTO);
        taskDTO.setTaskId(queryReward.getId());
        taskMapper.save(taskDTO);
        DeviceDTO dto = new DeviceDTO();
        dto.setOpenId(taskDTO.getOpenId());
        dto.setGameId(taskDTO.getGameId());
        dto.setScores(queryReward.getScores());
        deviceMapper.addScore(dto);
        DeviceDTO resultDTo = deviceMapper.selectByOpenId(dto);
        webResult.setData(resultDTo.getScores());
        webResult.setCode("200");
        return webResult;
    }

}
