package com.ovft.configure.sys.service;

import com.ovft.configure.sys.bean.Shop;
import com.ovft.configure.sys.bean.Warehouse;

import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
public interface WarehouseService {

    public List<Shop> findAllShop();

    public List<Warehouse> findAllMyWareHouse(Warehouse warehouse);

    public int buyProduct(Warehouse warehouse);

}
