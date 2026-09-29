package com.deneme.payment.mapper;

import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

}