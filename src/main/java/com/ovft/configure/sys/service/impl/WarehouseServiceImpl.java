package com.ovft.configure.sys.service.impl;

import com.ovft.configure.sys.bean.BuildDTO;
import com.ovft.configure.sys.bean.DeviceDTO;
import com.ovft.configure.sys.bean.Shop;
import com.ovft.configure.sys.bean.Warehouse;
import com.ovft.configure.sys.dao.BuildMapper;
import com.ovft.configure.sys.dao.DeviceMapper;
import com.ovft.configure.sys.dao.WarehouseMapper;
import com.ovft.configure.sys.service.WarehouseService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private BuildMapper buildMapper;

    public List<Shop> findAllShop(){
        return warehouseMapper.findAllShop();
    }

    public List<Warehouse> findAllMyWareHouse(Warehouse warehouse){
        return warehouseMapper.findAllMyWareHouse(warehouse);
    }

    public int buyProduct(Warehouse warehouse){
        Shop request = new Shop();
        request.setId(warehouse.getShopId());
        Shop shop = warehouseMapper.findById(request);
        DeviceDTO tempDevice = new DeviceDTO();
        tempDevice.setOpenId(warehouse.getOpenId());
        tempDevice.setGameId(warehouse.getGameId());
        DeviceDTO deviceDTO = deviceMapper.selectByOpenId(tempDevice);
        if (deviceDTO.getScores()<shop.getPrice()*warehouse.getNum()){
            return 9;
        }
        Warehouse tempResult = warehouseMapper.findExsit(warehouse);
        if (tempResult ==null || StringUtils.isEmpty(tempResult.getOpenId())){
            warehouse.setDestroyPrice(shop.getDestroyPrice());
            warehouse.setPrice(shop.getPrice());
            warehouse.setUrl1(shop.getUrl1());
            warehouse.setUrl2(shop.getUrl2());
            warehouse.setUrl3(shop.getUrl3());
            warehouseMapper.save(warehouse);
        }else{
            tempResult.setNum(tempResult.getNum()+warehouse.getNum());
            warehouseMapper.update(tempResult);
        }
        deviceDTO.setScores(-shop.getPrice());
        deviceMapper.addScore(deviceDTO);
        return 1;


    }


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
            temp.setNum(temp.getNum()-1);
            warehouseMapper.update(temp);
            return 1;
        }
    }

    public List<BuildDTO> findMyBuild(BuildDTO buildDTO){
        return buildMapper.findMyBuild(buildDTO);
    }

    public void destroy(BuildDTO buildDTO){
        buildMapper.del(buildDTO);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setOpenId(buildDTO.getOpenId());
        deviceDTO.setGameId(buildDTO.getGameId());
        deviceDTO.setScores(buildDTO.getDestroyPrice());
        deviceMapper.addScore(deviceDTO);
    }

}
