package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.Rank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2019/1/14.
 */
@Mapper
public interface RankMapper {


    public void save(Rank rank);

    public List<Rank> findRank();

    public void delAll();
}
