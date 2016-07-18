package com.github.vkuzel.orm_frameworks_demo.mybatis.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface ExtendedRegistrationMapper extends RegistrationMapper {

    // Callable queries has to be wrapped to {} so out parameter can be
    // registered.
    @Select("{call register_new_plane(#{planeId}, #{operatorId}, #{registrationNumber}, #{registrationId, mode=OUT, jdbcType=BIGINT})}")
    @Options(statementType = StatementType.CALLABLE)
    void registerNewPlane(RegisterNewPlaneParameters parameters);

    class RegisterNewPlaneParameters {

        private long planeId;
        private long operatorId;
        private String registrationNumber;
        private long registrationId;

        public long getPlaneId() {
            return planeId;
        }

        public void setPlaneId(long planeId) {
            this.planeId = planeId;
        }

        public long getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(long operatorId) {
            this.operatorId = operatorId;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public long getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(long registrationId) {
            this.registrationId = registrationId;
        }
    }

}
