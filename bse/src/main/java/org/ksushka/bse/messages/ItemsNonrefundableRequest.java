package org.ksushka.bse.messages;

import java.util.List;

public class ItemsNonrefundableRequest {

    public ItemsNonrefundableRequest() {}

    public Integer userId;

    //public int shopId;

    public List<Integer> itemIds;

    public ItemsNonrefundableRequest(Integer userId, List<Integer> itemIds) {
        this.userId = userId;
        //this.shopId = shopId;
        this.itemIds = itemIds;
    }
}
