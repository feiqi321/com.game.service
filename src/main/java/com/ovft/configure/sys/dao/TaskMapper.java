package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.TaskDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/27.
 */
@Mapper
public interface TaskMapper {

    public List<TaskDTO> findByTask(TaskDTO taskDTO);

}
