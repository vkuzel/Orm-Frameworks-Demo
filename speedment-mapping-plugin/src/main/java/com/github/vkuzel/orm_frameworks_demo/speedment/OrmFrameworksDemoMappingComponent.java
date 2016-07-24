package com.github.vkuzel.orm_frameworks_demo.speedment;

import com.github.vkuzel.orm_frameworks_demo.speedment.mapper.PlaneDimensionsMapper;
import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.internal.core.platform.component.impl.AbstractComponent;
import com.speedment.internal.license.AbstractSoftware;
import com.speedment.internal.license.OpenSourceLicense;
import com.speedment.license.Software;

public class OrmFrameworksDemoMappingComponent extends AbstractComponent {
    public OrmFrameworksDemoMappingComponent(Speedment speedment) {
        super(speedment);
    }

    @Override
    public void onResolve() {
        getSpeedment().getTypeMapperComponent().install(PlaneDimensionsMapper::new);
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return OrmFrameworksDemoMappingComponent.class;
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with(
                "Orm Frameworks Demo Mapping Component",
                "1.0",
                OpenSourceLicense.APACHE_2
        );
    }

    @Override
    public Component defaultCopy(Speedment speedment) {
        return new OrmFrameworksDemoMappingComponent(speedment);
    }
}
