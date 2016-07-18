package com.github.vkuzel.orm_frameworks_demo.mybatis.adapter;

import com.github.vkuzel.orm_frameworks_demo.mybatis.model.Operator;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;

public class OperatorWrapper extends Operator implements OperatorDetail {

    public OperatorWrapper() {
    }

    public OperatorWrapper(Operator operator) {
        setId(operator.getId());
        setName(operator.getName());
    }
}
