package org.ksushka.bse.controllers;

import org.ksushka.bse.messages.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class BSEFrontendController {
    @Value("${server.port}")
    private int port;

    @GetMapping("")
    public String bse(
            ModelMap m,
            HttpSession s,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId
    ) {
        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/get_products";
        GetProductsResponse resp = rt.getForObject(url, GetProductsResponse.class);
        m.put("lc_user_id", lcUserId);
        m.put("products", resp.products);
        return "bse";
    }

    @PostMapping(value = "create_order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createOrder(
            ModelMap m,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId,
            @RequestParam String products
    ) {
        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/create_order";
        try {
            CreateOrderRequest reqBody = new CreateOrderRequest(
                    lcUserId,
                    Arrays.stream(products.split(",")).map(Integer::parseInt).collect(Collectors.toList())
            );

            CreateOrderResponse resp = rt.postForObject(url, reqBody, CreateOrderResponse.class);

            if (resp == null) {
                m.put("message", "Empty response from API");
                return "message";
            }
            if (resp.status.equals("error")) {
                m.put("message", resp.error);
                return "message";
            }
            return "redirect:/order?id=" + resp.id;
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            return "message";
        } catch (NumberFormatException ex) {
            m.put("message", "Неверный формат списка товаров или корзина пуста");
            return "message";
        }
    }

    @GetMapping("order")
    public String order(
            ModelMap m,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId,
            @RequestParam(name = "id", required = false) Integer orderId
    ) {
        RestTemplate rt = new RestTemplate();

        final String url = "http://localhost:" + port + "/api/get_order_info?id={id}";

        try {
            GetOrderInfoResponse resp = rt.getForObject(
                    url, GetOrderInfoResponse.class, orderId);
            if (resp == null) {
                m.put("message", "Empty response body");
                return "message";
            }
            if (resp.status.equals("ok")) {
                m.put("order", resp.order);
                return "order";
            } else {
                m.put("message", resp.error);
                return "message";
            }
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            return "message";
        }
    }

    @GetMapping("orders")
    public String orders(
            ModelMap m,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId
    ) {
        RestTemplate rt = new RestTemplate();

        final String url = "http://localhost:" + port + "/api/get_orders";

        try {
            GetOrdersResponse resp = rt.getForObject(
                    url, GetOrdersResponse.class);
            if (resp == null) {
                m.put("message", "Empty response body");
                return "message";
            }
            if (resp.status.equals("ok")) {
                m.put("orders", resp.orders);
                return "orders";
            } else {
                m.put("message", resp.error);
                return "message";
            }
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            return "message";
        }
    }

    @PostMapping(value = "refund", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String refund(
            ModelMap m,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId,
            @RequestParam(name = "item_id", required = false) Integer itemId
    ) {
        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/get_refund";

        try {

            RefundResponse resp = rt.postForObject(url, new RefundRequest(lcUserId, itemId), RefundResponse.class);
            if (resp == null) {
                m.put("message", "Empty response from API");
                return "message";
            }
            if (resp.status.equals("error")) {
                m.put("message", resp.error);
                return "message";
            }
            return "redirect:/order?id=" + resp.orderId;
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            return "message";
        }
    }

    @PostMapping(value = "nonrefundable", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String nonrefundable(
            ModelMap m,
            HttpServletRequest req,
            @RequestParam(name = "lc_user_id", required = false) Integer lcUserId,
            @RequestParam(name = "order_id", required = false) Integer orderId
    ) {
        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/make_nonrefundable";

        try {

            NonRefundableResponse resp = rt.postForObject(url, new NonRefundableRequest(orderId), NonRefundableResponse.class);
            if (resp == null) {
                m.put("message", "Empty response from API");
                return "message";
            }
            if (resp.status.equals("error")) {
                m.put("message", resp.error);
                return "message";
            }
            return "redirect:/order?id=" + resp.orderId;
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            return "message";
        }
    }
}