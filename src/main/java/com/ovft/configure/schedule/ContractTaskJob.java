package com.ovft.configure.schedule;

import com.ovft.configure.utils.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by looyer on 2017/8/30.
 */
@Component
public class ContractTaskJob {

    private static Logger logger = LoggerFactory.getLogger(ContractTaskJob.class);


    /**
     * 每天0点开始执行，每隔1分钟执行一次,直至23点,改变数字
     */
    @Scheduled(cron = "0 0/1 0-23 * * ?")
    public void runContractTask() {

        try {

            if (GlobalUtils.animationID == 3){
                GlobalUtils.animationID = 1;
            }else {
                GlobalUtils.animationID = GlobalUtils.animationID + 1;
            }
            logger.info("GlobalUtils.animationID 当前值为："+GlobalUtils.animationID );
            if (GlobalUtils.musicID == 3){
                GlobalUtils.musicID = 1;
            }else {
                GlobalUtils.musicID = GlobalUtils.musicID + 1;
            }
            logger.info("GlobalUtils.musicID 当前值为："+GlobalUtils.musicID );
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
