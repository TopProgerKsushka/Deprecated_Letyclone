package org.ksushka.letyclone.controllers;

import org.ksushka.letyclone.messages.*;
import org.ksushka.letyclone.model.*;
import org.ksushka.letyclone.services.CashbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/partner_api")
public class PartnerAPI {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CashbackService cashbackService;

    @PostMapping(value = "user_created_order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCreatedOrderResponse userCreatedOrder(@RequestBody UserCreatedOrderRequest req, Principal principal) {
        try {
            cashbackService.userCreatedOrder(principal.getName(), req.userId, req.items);
            return UserCreatedOrderResponse.ok();
        } catch (RuntimeException ex) {
            return UserCreatedOrderResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping(value = "item_refunded", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemRefundedResponse itemRefunded(@RequestBody ItemRefundedRequest req, Principal principal) {
        try {
            cashbackService.itemRefunded(req.userId, principal.getName(), req.shopOrderedItemId);
            return ItemRefundedResponse.ok();
        } catch (RuntimeException ex) {
            return ItemRefundedResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping(value = "items_nonrefundable", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemsNonrefundableResponse itemsNonrefundable(@RequestBody ItemsNonrefundableRequest req, Principal principal) {
        try {
            cashbackService.itemsNonrefundable(req.userId, principal.getName(), req.itemIds);
            return ItemsNonrefundableResponse.ok();
        } catch (RuntimeException ex) {
            return ItemsNonrefundableResponse.error(ex.getLocalizedMessage());
        }
    }
}
