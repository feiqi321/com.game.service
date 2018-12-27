package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.EventConfigDTO;
import com.ovft.configure.sys.bean.TaskDTO;
import com.ovft.configure.sys.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
@RestController
@RequestMapping(value = "/game/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskService taskService;

    /**
     *  查询事件对应的时间
     *
     * @return
     */
    @PostMapping(value = "/findByTask")
    public WebResult findByTask(@RequestBody TaskDTO taskDTO)  {
        logger.info("查询事件对应的时间");
        WebResult result = new WebResult();
        try {
            List<TaskDTO> resultTasks = taskService.findByTask(taskDTO);
            result.setData(resultTasks);
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }


}
