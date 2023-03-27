package com.shopping.orderservice.service;

import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.entity.Order;
import com.shopping.orderservice.entity.OrderLineItems;
import com.shopping.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private ModelMapper modelMapper;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        log.info("Items List {}", orderRequest.getOrderLineItemsDtoListDto());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoListDto()
                .stream()
                .map(item -> modelMapper.map(item, OrderLineItems.class))
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        orderRepository.save(order);
    }
}
