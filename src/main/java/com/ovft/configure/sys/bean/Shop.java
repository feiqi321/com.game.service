package com.ovft.configure.sys.bean;

import java.io.Serializable;

/**
 * Created by looyer on 2019/1/7.
 */
public class Shop implements Serializable {

    private int id;

    private int price;

    private  String name;

    private int destroyPrice;

    private String url1;

    private String url2;

    private String url3;

    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestroyPrice() {
        return destroyPrice;
    }

    public void setDestroyPrice(int destroyPrice) {
        this.destroyPrice = destroyPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
