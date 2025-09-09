package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateOrderResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateOrderRequestMapper;
import com.pragma.fc.food_curt.application.mapper.ICreateOrderResponseMapper;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.infraestructure.input.security.entity.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {
    private final ICreateOrderRequestMapper createOrderRequestMapper;
    private final ICreateOrderResponseMapper createOrderResponseMapper;
    private final IOrderServicePort orderServicePort;
    private final ITokenServicePort tokenServicePort;

    @Override
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto dto) {
        Long customerDocumentNumber = extractDocumentNumber();
        Order order = createOrderRequestMapper.toModel(dto);
        Order createdOrder = orderServicePort.createOrder(order, customerDocumentNumber);
        return createOrderResponseMapper.toDto(createdOrder);
    }

    private Long extractDocumentNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long documentNumber = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            documentNumber = Long.parseLong(tokenServicePort
                    .extractSubject(jwtAuthenticationToken.getToken())
            );
        }

        return documentNumber;
    }
}
