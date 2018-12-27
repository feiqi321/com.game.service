package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.TaskDTO;

import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
public interface TaskService {

    public List<TaskDTO> findByTask(TaskDTO taskDTO);

}
