package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.BuildDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
@Mapper
public interface BuildMapper {

    public void save(BuildDTO buildDTO);

    public void del(BuildDTO buildDTO);

    public List<BuildDTO> findMyBuild(BuildDTO buildDTO);

    public BuildDTO findMyBuildOne(BuildDTO buildDTO);

}
