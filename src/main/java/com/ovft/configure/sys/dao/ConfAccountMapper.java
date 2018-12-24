package com.ovft.configure.sys.dao;

import com.ovft.configure.sys.bean.ConfAccountDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by looyer on 2018/12/4.
 */
@Mapper
public interface ConfAccountMapper {

    public ConfAccountDO findById(int id);

    public List<ConfAccountDO> findByProperty(ConfAccountDO confAccountDO);

    public int findCountByProperty(ConfAccountDO confAccountDO);

    public void saveConfAccount(ConfAccountDO confAccountDO);

    public void updateConfAccount(ConfAccountDO confAccountDO);

    public void deleteConfAccount(ConfAccountDO confAccountDO);
}
