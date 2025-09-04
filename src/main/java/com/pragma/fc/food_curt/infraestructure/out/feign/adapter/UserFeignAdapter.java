package com.pragma.fc.food_curt.infraestructure.out.feign.adapter;

import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.IUserClientFeign;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.response.UserRoleResponseDto;
import com.pragma.fc.food_curt.infraestructure.out.feign.entity.UserRole;

public class UserFeignAdapter implements IUserClientPort {
    private final IUserClientFeign userClientFeign;

    public UserFeignAdapter(IUserClientFeign userClientFeign) {
        this.userClientFeign = userClientFeign;
    }

    @Override
    public Boolean isOwner(Long documentNumber) {
        ApiSuccess<UserRoleResponseDto> response = userClientFeign.validateUserRole(documentNumber);

        if (response == null || response.getPayload() == null || response.getPayload().getName() == null) {
            return false;
        }

        return UserRole.OWNER.name().equals(response.getPayload().getName());
    }

}
