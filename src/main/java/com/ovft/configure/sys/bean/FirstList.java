package com.ovft.configure.sys.bean;

import java.util.List;

/**
 * Created by looyer on 2019/1/19.
 */
public class FirstList {

    private List<CollectingDTO> collectList;

    private List<Boolean> shList;

    private List<Integer> singleList;

    public List<CollectingDTO> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<CollectingDTO> collectList) {
        this.collectList = collectList;
    }

    public List<Boolean> getShList() {
        return shList;
    }

    public void setShList(List<Boolean> shList) {
        this.shList = shList;
    }

    public List<Integer> getSingleList() {
        return singleList;
    }

    public void setSingleList(List<Integer> singleList) {
        this.singleList = singleList;
    }
}
