package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.CollectDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/24.
 */
@Mapper
public interface CollectMapper {

    public CollectDTO findByOpenId(CollectDTO collectDTO);

    public List<CollectDTO> listAllByOpenId(CollectDTO collectDTO);

    public void save(CollectDTO collectDTO);

    public void update(CollectDTO collectDTO);

    public void complete(CollectDTO collectDTO);

    public void view(CollectDTO collectDTO);

    public int findNewNum(CollectDTO collectDTO);

}
