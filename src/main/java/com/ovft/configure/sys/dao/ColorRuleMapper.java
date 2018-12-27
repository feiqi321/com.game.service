package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.ColorRuleDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by looyer on 2018/12/27.
 */
@Mapper
public interface ColorRuleMapper {

        public ColorRuleDTO findRule(ColorRuleDTO colorRuleDTO);

}
