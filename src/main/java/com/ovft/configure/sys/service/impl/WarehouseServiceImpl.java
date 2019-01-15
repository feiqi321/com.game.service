package com.ovft.configure.sys.service.impl;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.*;
import com.ovft.configure.sys.dao.*;
import com.ovft.configure.sys.service.WarehouseService;
import com.ovft.configure.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private BuildMapper buildMapper;
    @Resource
    private AttackMapper attackMapper;


    @Override
    public List<Shop> findAllShop(Shop shop){
        return warehouseMapper.findAllShop(shop);
    }
    @Override
    public List<Warehouse> findAllMyWareHouse(Warehouse warehouse){
        return warehouseMapper.findAllMyWareHouse(warehouse);
    }
    @Override
    public WebResult buyProduct(Warehouse warehouse){
        WebResult result = new WebResult();
        Shop request = new Shop();
        request.setId(warehouse.getShopId());
        Shop shop = warehouseMapper.findById(request);
        DeviceDTO tempDevice = new DeviceDTO();
        tempDevice.setOpenId(warehouse.getOpenId());
        tempDevice.setGameId(warehouse.getGameId());
        DeviceDTO deviceDTO = deviceMapper.selectByOpenId(tempDevice);
        if (deviceDTO.getScores()<shop.getPrice()*warehouse.getNum()){
            result.setMsg("积分不够,购买失败");
            result.setCode("500");
            return result;
        }
        Warehouse tempResult = warehouseMapper.findExsit(warehouse);
        if (tempResult ==null || StringUtils.isEmpty(tempResult.getOpenId())){
            warehouse.setDestroyPrice(shop.getDestroyPrice());
            warehouse.setPrice(shop.getPrice());
            warehouse.setType(shop.getType());
            warehouse.setUrl1(shop.getUrl1());
            warehouse.setUrl2(shop.getUrl2());
            warehouse.setUrl3(shop.getUrl3());
            warehouseMapper.save(warehouse);
        }else{
            tempResult.setNum(tempResult.getNum()+warehouse.getNum());
            warehouseMapper.update(tempResult);
        }
        deviceDTO.setScores(-shop.getPrice());
        deviceMapper.reduce(deviceDTO);
        DeviceDTO resultDTO = deviceMapper.selectByOpenId(deviceDTO);
        result.setCode("200");
        result.setData(resultDTO.getScores());
        return result;

    }

    @Override
    public int build(BuildDTO buildDTO){
        Warehouse temp = new Warehouse();
        temp.setId(buildDTO.getWareId());
        Warehouse tempResult = warehouseMapper.findWareById(temp);
        if (tempResult ==null || StringUtils.isEmpty(tempResult.getOpenId())){
            return 9;//建筑不存在
        }else if (tempResult.getNum()<=0){
            return 8;//数量不足
        }else{
            buildDTO.setUrl(tempResult.getUrl3());
            buildDTO.setDestroyPrice(tempResult.getDestroyPrice());
            buildMapper.save(buildDTO);
            temp.setNum(tempResult.getNum()-1);
            warehouseMapper.update(temp);
            return 1;
        }
    }
    @Override
    public List<BuildDTO> findMyBuild(BuildDTO buildDTO){
        return buildMapper.findMyBuild(buildDTO);
    }
    @Override
    public WebResult destroy(BuildDTO buildDTO){
        WebResult result = new WebResult();
        buildMapper.del(buildDTO);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setOpenId(buildDTO.getOpenId());
        deviceDTO.setGameId(buildDTO.getGameId());
        DeviceDTO resultDTO = deviceMapper.selectByOpenId(deviceDTO);
        result.setCode("200");
        result.setData(resultDTO.getScores());
        return result;
    }
    @Override
    public void bossDestroy(){

        String gameId = GlobalUtils.mapCache.get("gameId")==null?"":GlobalUtils.mapCache.get("gameId").toString();
        AttackDTO attackDTO = new AttackDTO();
        attackDTO.setGameId(gameId);
        List<AttackDTO> list  = attackMapper.findLastThree(attackDTO);
        for (int i=0;i<list.size();i++){
            AttackDTO tempDTO = list.get(i);
            BuildDTO buildDTO = new BuildDTO();
            buildDTO.setGameId(tempDTO.getGameId());
            buildDTO.setOpenId(tempDTO.getOpenId());
            BuildDTO temp = buildMapper.findMyBuildOne(buildDTO);
            buildMapper.del(temp);
            Warehouse warehouse= new Warehouse();
            warehouse.setId(temp.getWareId());
            warehouse = warehouseMapper.findWareById(warehouse);
            DeviceDTO deviceDTO = new DeviceDTO();
            deviceDTO.setGameId(tempDTO.getGameId());
            deviceDTO.setOpenId(tempDTO.getOpenId());
            deviceDTO.setScores(-warehouse.getPrice());
            deviceMapper.addBossDestroyScore(deviceDTO);
        }

    }

}
