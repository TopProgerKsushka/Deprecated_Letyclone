package org.ksushka.bse.controllers;

import org.ksushka.bse.messages.*;
import org.ksushka.bse.model.OrderedItem;
import org.ksushka.bse.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BSEAPIController {
    @Autowired
    OrderService orderService;

    @GetMapping("get_products")
    public GetProductsResponse getProducts() {
        return GetProductsResponse.ok(orderService.getProducts());
    }

    @PostMapping("create_order")
    public CreateOrderResponse createOrder(HttpServletRequest req, @RequestBody CreateOrderRequest reqBody) {
        try {
            return CreateOrderResponse.ok(orderService.createOrder(reqBody.productIds, reqBody.lcUserId));
        } catch (RuntimeException ex) {
            return CreateOrderResponse.error(ex.getLocalizedMessage());
        }
    }

    @GetMapping("get_order_info")
    public GetOrderInfoResponse getOrderInfo(@RequestParam(required = false) Integer id) {
        try {
            return GetOrderInfoResponse.ok(orderService.getOrderInfo(id));
        } catch (RuntimeException ex) {
            return GetOrderInfoResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping("get_refund")
    public RefundResponse getRefund(HttpServletRequest req, @RequestBody RefundRequest reqBody) {
        try {
            OrderedItem item = orderService.getRefund(reqBody.itemId, reqBody.lcUserId);
            return RefundResponse.ok(item, item.getOrder().getId());
        } catch (RuntimeException ex) {
            return RefundResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping("make_nonrefundable")
    public NonRefundableResponse makeNonRefundable(HttpServletRequest req, @RequestBody NonRefundableRequest reqBody) {
        try {
            orderService.makeNonRefundable(reqBody.orderId);
            return NonRefundableResponse.ok(reqBody.orderId);
        } catch (RuntimeException ex) {
            return NonRefundableResponse.error(ex.getLocalizedMessage());
        }
    }

    @GetMapping("get_orders")
    public GetOrdersResponse getOrders() {
        try {
            return GetOrdersResponse.ok(orderService.getOrders());
        } catch (RuntimeException ex) {
            return GetOrdersResponse.error(ex.getLocalizedMessage());
        }
    }
}
