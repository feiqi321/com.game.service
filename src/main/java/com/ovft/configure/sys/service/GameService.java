package com.ovft.configure.sys.service;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.AttackDTO;
import com.ovft.configure.sys.bean.Rank;

import java.util.List;

/**
 * Created by looyer on 2019/1/6.
 */
public interface GameService {

    public void startGame();

    public void endGame();

    public AttackDTO findTotalAttack(AttackDTO attackDTO);

    public WebResult listRank();

}
