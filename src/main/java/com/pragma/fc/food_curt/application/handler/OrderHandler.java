package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.OrderResponseDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateOrderRequestMapper;
import com.pragma.fc.food_curt.application.mapper.IOrderPaginationResponseMapper;
import com.pragma.fc.food_curt.application.mapper.IOrderResponseMapper;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.infraestructure.input.security.entity.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {
    private final ICreateOrderRequestMapper createOrderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderPaginationResponseMapper paginationResponseMapper;
    private final IOrderServicePort orderServicePort;
    private final ITokenServicePort tokenServicePort;

    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto dto) {
        Long customerDocumentNumber = extractDocumentNumber();
        Order order = createOrderRequestMapper.toModel(dto);
        Order createdOrder = orderServicePort.createOrder(order, customerDocumentNumber);
        return orderResponseMapper.toDto(createdOrder);
    }

    @Override
    public PaginationResponseDto<OrderResponseDto> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> orderStatusId) {
        Long customerDocumentNumber = extractDocumentNumber();
        Pagination<Order> orderPagination = orderServicePort.getPaginatedByStatusSortedByDate(page, size, orderStatusId, customerDocumentNumber);
        return paginationResponseMapper.toDto(orderPagination);
    }

    @Override
    public OrderResponseDto assignWorkerToOrder(Integer orderId) {
        Long workerDocumentNumber = extractDocumentNumber();
        Order updatedOrder = orderServicePort.assignWorkerToOrder(orderId, workerDocumentNumber);
        return orderResponseMapper.toDto(updatedOrder);
    }

    @Override
    public OrderResponseDto markAsReady(Integer orderId) {
        Long workerDocumentNumber = extractDocumentNumber();
        Order updatedOrder = orderServicePort.markAsReady(orderId, workerDocumentNumber);
        return orderResponseMapper.toDto(updatedOrder);
    }

    @Override
    public OrderResponseDto finishOrder(Integer orderId, String otpCode) {
        Long workerDocumentNumber = extractDocumentNumber();
        Order updatedOrder = orderServicePort.finishOrder(orderId, workerDocumentNumber, otpCode);
        return orderResponseMapper.toDto(updatedOrder);
    }

    @Override
    public OrderResponseDto cancelOrder(Integer orderId) {
        Long customerDocumentNumber = extractDocumentNumber();
        Order updatedOrder = orderServicePort.cancelOrder(orderId, customerDocumentNumber);
        return orderResponseMapper.toDto(updatedOrder);
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
