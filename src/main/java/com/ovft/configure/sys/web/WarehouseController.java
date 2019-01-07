package com.ovft.configure.sys.web;

import com.ovft.configure.http.result.WebResult;
import com.ovft.configure.sys.bean.Shop;
import com.ovft.configure.sys.bean.Warehouse;
import com.ovft.configure.sys.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
@RestController
@RequestMapping(value = "/game/warehouse")
public class WarehouseController {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    /**
     *  查询商店所有商品
     *
     * @return
     */
    @PostMapping(value = "/findAllShop")
    public WebResult findAllShop()  {
        logger.info("查询商店所有商品");
        WebResult result = new WebResult();
        try {
            List<Shop> list = warehouseService.findAllShop();
            result.setData(list);
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }

    /**
     *  查询用户拥有的商品
     *
     * @return
     */
    @PostMapping(value = "/findAllMyWareHouse")
    public WebResult findAllMyWareHouse(Warehouse warehouse)  {
        logger.info("查询用户拥有的商品");
        WebResult result = new WebResult();
        try {
            List<Warehouse> list = warehouseService.findAllMyWareHouse(warehouse);
            result.setData(list);
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }

    /**
     *  购买
     *
     * @return
     */
    @PostMapping(value = "/buyProduct")
    public WebResult buyProduct(Warehouse warehouse)  {
        logger.info("购买");
        WebResult result = new WebResult();
        try {
            int flag = warehouseService.buyProduct(warehouse);
            if (flag==9){
                result.setMsg("积分不够,购买失败");
                result.setCode("200");
                return result;
            }
            result.setCode("200");
        }catch (Exception e){
            result.setCode("500");
            result.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return  result;
    }


}
