package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.GameDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2019/1/6.
 */
@Mapper
public interface GameMapper {

    public void save(GameDTO gameDTO);

    public void update();


    public GameDTO findNewGame(GameDTO gameDTO);

}
