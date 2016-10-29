package com.github.vkuzel.orm_frameworks_demo.activejdbc.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;

import java.util.Set;

public abstract class AuditModel extends Model {

    private void setAuditFields() {
        Set<String> attributeNames = ModelDelegate.attributeNames(this.getClass());
        if (getId() == null && attributeNames.contains("created_by") && get("created_by") == null) {
            setString("created_by", UsernameProvider.getUsername());
        }
        if (getId() != null && attributeNames.contains("updated_by")) {
            setString("updated_by", UsernameProvider.getUsername());
        }
    }

    @Override
    protected void beforeSave() {
        setAuditFields();
        super.beforeSave();
    }
}
