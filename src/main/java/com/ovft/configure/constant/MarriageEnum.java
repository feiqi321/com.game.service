package com.ovft.configure.constant;

import java.util.Arrays;

/**
 * FileName: MarriageEnum
 * Author:   caofanCPU
 * Date:     2018/8/20 14:24
 */

public enum MarriageEnum {
    
    UNMARRIED(1, "未婚"),
    MARRIED(2, "已婚"),
    DIVORCED(3, "离异"),
    BEREFT(4, "丧偶"),
    UNKNOWN(5, "不详");
    
    private Integer id;
    
    private String name;
    
    MarriageEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static MarriageEnum loadById(Integer id) {
        return Arrays.stream(MarriageEnum.values())
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(UNKNOWN);
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
}
