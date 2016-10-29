package com.github.vkuzel.orm_frameworks_demo.activejdbc.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.activejdbc.mapping.MappingUtils;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import org.javalite.activejdbc.Model;

public class Operator extends Model {

    private OperatorAdapter adapter = new OperatorAdapter();

    public OperatorDetail toOperatorDetail() {
        return adapter;
    }

    public class OperatorAdapter implements OperatorDetail {

        private OperatorAdapter() {
        }

        public Operator toOperator() {
            return Operator.this;
        }

        @Override
        public Long getId() {
            return getLongId();
        }

        @Override
        public void setId(Long id) {
            Operator.this.setId(id);
        }

        @Override
        public JsonNode getName() {
            return MappingUtils.toJson(get("name"));
        }

        @Override
        public void setName(JsonNode name) {
            set("name", MappingUtils.fromJson(name));
        }
    }
}
