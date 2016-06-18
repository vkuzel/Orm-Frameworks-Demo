package com.github.vkuzel.orm_frameworks_demo.hibernate.auditing;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class UsernameAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return UsernameProvider.getUsername();
    }
}
