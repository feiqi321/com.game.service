package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.AttackDTO;

/**
 * Created by looyer on 2019/1/6.
 */
public interface GameService {

    public void startGame();

    public void endGame();

    public AttackDTO findTotalAttack(AttackDTO attackDTO);

}
