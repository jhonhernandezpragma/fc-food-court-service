package com.pragma.fc.food_curt.infraestructure.out.feign.adapter;

import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.IUserClientFeign;
import com.pragma.fc.food_curt.infraestructure.out.feign.entity.UserRole;

public class UserFeignAdapter implements IUserClientPort {
    private final IUserClientFeign userClientFeign;

    public UserFeignAdapter(IUserClientFeign userClientFeign) {
        this.userClientFeign = userClientFeign;
    }

    @Override
    public Boolean isOwner(Long documentNumber) {
        String userRole = userClientFeign.validateUserRole(documentNumber).getPayload().getName();
        return UserRole.OWNER.name().equals(userRole);
    }
}
