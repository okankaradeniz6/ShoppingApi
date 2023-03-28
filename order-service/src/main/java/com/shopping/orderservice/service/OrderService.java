package com.shopping.orderservice.service;

import com.shopping.orderservice.dto.InventoryResponse;
import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.entity.Order;
import com.shopping.orderservice.entity.OrderLineItems;
import com.shopping.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        log.info("Items List {}", orderRequest.getOrderLineItemsDtoListDto());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoListDto()
                .stream()
                .map(item -> modelMapper.map(item, OrderLineItems.class))
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //check if, are there items in the stock or not by calling inventory-ms
        InventoryResponse[] inventoryResponsesArray = webClient.get()
                        .uri("http://localhost:8082/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResponse[].class)
                                                .block();

        log.info("Response: " + Arrays.stream(inventoryResponsesArray).toList().isEmpty());
        //If the inventoryResponseArray is empty, allMatch gives true, we need to prevent
        //that by check whether the array is empty or not.
        boolean allProductsIsInStock = !Arrays.stream(inventoryResponsesArray).toList().isEmpty() && Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);
        log.info("allProducts is in stock " + allProductsIsInStock);
        if(allProductsIsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("In stock, we don't have this items, please try again");
        }


    }
}
