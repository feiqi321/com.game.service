package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.Shop;
import com.ovft.configure.sys.bean.Warehouse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2019/1/7.
 */
@Mapper
public interface WarehouseMapper {

    public List<Shop> findAllShop();

    public List<Warehouse> findAllMyWareHouse(Warehouse warehouse);

    public Shop findById(Shop shop);

    public Warehouse findExsit(Warehouse warehouse);

    public void save(Warehouse warehouse);

    public void update(Warehouse warehouse);

    public Warehouse findWareById(Warehouse warehouse);


}
