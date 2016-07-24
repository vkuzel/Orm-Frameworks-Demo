package com.github.vkuzel.orm_frameworks_demo.speedment;

import com.speedment.Speedment;
import com.speedment.component.ComponentConstructor;

public class OrmFrameworksDemoMappingComponentConstructor implements ComponentConstructor<OrmFrameworksDemoMappingComponent> {
    @Override
    public OrmFrameworksDemoMappingComponent create(Speedment speedment) {
        return new OrmFrameworksDemoMappingComponent(speedment);
    }
}
