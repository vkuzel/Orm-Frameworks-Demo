package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import com.github.vkuzel.orm_frameworks_demo.transport.PlaneSimpleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SimplePlaneLoader {

    Page<PlaneSimpleDetail> findAllSimplePlanes(Pageable pageable);
}
