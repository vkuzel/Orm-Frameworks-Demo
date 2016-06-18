package com.github.vkuzel.orm_frameworks_demo.transport;

import com.github.vkuzel.orm_frameworks_demo.common.Utils;

import java.util.Map;

public interface OperatorDetail {

    Long getId();

    void setId(Long id);

    Map<String, String> getName();

    void setName(Map<String, String> name);
}
