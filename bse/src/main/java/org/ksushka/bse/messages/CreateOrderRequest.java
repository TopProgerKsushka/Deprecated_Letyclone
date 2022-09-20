package org.ksushka.bse.messages;

import java.util.List;

public class CreateOrderRequest {
    public Integer lcUserId;
    public List<Integer> productIds;
    public CreateOrderRequest(Integer lcUserId, List<Integer> productsId) {
        this.lcUserId = lcUserId;
        this.productIds = productsId;
    }
}

