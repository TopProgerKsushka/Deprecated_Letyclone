package org.ksushka.bse.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.ksushka.bse.messages.*;
import org.ksushka.bse.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Value("${letyclone.port}")
    private int letyclonePort;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderedItemRepository orderedItemRepository;

    @Transactional
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public int createOrder(List<Integer> productIds, Integer lcUserId) {
        if (productIds.isEmpty()) throw new RuntimeException("Корзина не должна быть пуста");

        Order order = new Order(lcUserId);
        orderRepository.save(order);

        List<UserCreatedOrderRequest.Item> items = new ArrayList<>();

        for (int productId : productIds) {
            Product product = productRepository.findById(productId);
            OrderedItem orderedItem = new OrderedItem(order, product);
            orderedItemRepository.save(orderedItem);

            double cashback = Math.floor(product.getPrice() * product.getCashbackPercent()) / 100;
            items.add(new UserCreatedOrderRequest.Item(orderedItem.getId(), cashback, orderedItem.getCanBeRefunded(), orderedItem.getRefunded()));
        }

        if (lcUserId != null) {

            try {
                RestTemplate rt = new RestTemplate();
                HttpHeaders headers = createHeaders("bse", "5qlx0cSyDW6niSeqR0qC");
                final String url = "http://localhost:" + letyclonePort + "/partner_api/user_created_order";

                UserCreatedOrderResponse resp = rt.postForObject(
                        url,
                        new HttpEntity(
                                new UserCreatedOrderRequest(
                                        lcUserId,
                                        items
                                ),
                                headers
                        ),
                        UserCreatedOrderResponse.class
                );
                if (resp == null || resp.status.equals("error")) {
                    throw new RuntimeException("Не удалось зарегистрировать заказ на сервисе letyclone");
                }
            } catch (RestClientException ex) {
                throw new RuntimeException("Ошибка доступа к API letyclone");
            }
        }

        orderRepository.save(order);
        return order.getId();
    }
    @Transactional
    public Order getOrderInfo(Integer id) {
        if (id == null) throw new RuntimeException("Не указан ID заказа");
        Order order = orderRepository.findById(id.intValue());
        if (order == null) throw new RuntimeException("Заказ с данным ID не был найден");
        return order;
    }

    @Transactional
    public OrderedItem getRefund(int itemId, Integer lcUserId) {
        OrderedItem item = orderedItemRepository.findById(itemId);
        if (!item.getCanBeRefunded())
            throw new RuntimeException("Товар не может быть возвращен");
        if (item.getRefunded() == true)
            throw new RuntimeException("Товар уже был возвращен");
        item.setRefunded(true);
        if (lcUserId != null) {
            try {
                RestTemplate rt = new RestTemplate();
                HttpHeaders headers = createHeaders("bse", "5qlx0cSyDW6niSeqR0qC");
                final String url = "http://localhost:" + letyclonePort + "/partner_api/item_refunded";
                ItemRefundedResponse resp = rt.postForObject(
                        url,
                        new HttpEntity(
                                new ItemRefundedRequest(lcUserId, item.getId()),
                                headers
                        ),
                        ItemRefundedResponse.class
                );
                if (resp == null || resp.status.equals("error")) {
                    throw new RuntimeException("Не удалось вернуть заказ на сервисе letyclone");
                }
            } catch (RestClientException ex) {
                throw new RuntimeException("Ошибка доступа к API letyclone");
            }
        }
        orderedItemRepository.save(item);
        return item;
    }

    @Transactional
    public void makeNonRefundable(Integer orderId) {
        Order order = orderRepository.findById(orderId.intValue());
        if (order == null) throw new RuntimeException("Заказ не был получен");
        List<OrderedItem> items = orderedItemRepository.findAllByOrder(order);
        if (items == null) throw new RuntimeException("Товары не были получены");
        if (!items.get(0).getCanBeRefunded()) throw new RuntimeException("Товары уже нельзя вернуть");

        for (OrderedItem item : items) {
            item.setCanBeRefunded(false);
            orderedItemRepository.save(item);
        }

        if (order.getLetycloneUserId() != null) {
            try {
                RestTemplate rt = new RestTemplate();
                HttpHeaders headers = createHeaders("bse", "5qlx0cSyDW6niSeqR0qC");
                final String url = "http://localhost:" + letyclonePort + "/partner_api/items_nonrefundable";
                ItemsNonrefundableResponse resp = rt.postForObject(
                        url,
                        new HttpEntity(
                                new ItemsNonrefundableRequest(
                                        order.getLetycloneUserId(),
                                        items.stream().map(OrderedItem::getId).collect(Collectors.toList())),
                                headers
                        ),
                                ItemsNonrefundableResponse.class);
                if (resp == null) {
                    throw new RuntimeException("Не удалось сделать товары невозвратными на сервисе letyclone");
                }
                if (resp.status.equals("error")) {
                    throw new RuntimeException("Не удалось сделать товары невозвратными на сервисе letyclone: " + resp.error);
                }
            } catch (RestClientException ex) {
                throw new RuntimeException("Ошибка доступа к API letyclone");
            }
        }
    }

    @Transactional
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
