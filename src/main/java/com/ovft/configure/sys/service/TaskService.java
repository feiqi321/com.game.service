package com.ovft.configure.sys.service;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.TaskDTO;

import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
public interface TaskService {

    public TaskDTO findByTask(TaskDTO taskDTO);

    public WebResult reward(TaskDTO taskDTO);

}
