package com.github.vkuzel.orm_frameworks_demo.mybatis.dao;

import com.github.vkuzel.orm_frameworks_demo.mybatis.adapter.OperatorWrapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.model.Operator;
import org.apache.ibatis.annotations.Select;

public interface ExtendedOperatorMapper extends OperatorMapper {

    @Select("SELECT * FROM operators WHERE name ->> #{0} = #{1}")
    OperatorWrapper selectByName(String languageCode, String name);
}
