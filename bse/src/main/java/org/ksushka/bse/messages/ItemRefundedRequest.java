package org.ksushka.bse.messages;

public class ItemRefundedRequest {
    public Integer userId;
    //public int shopId;
    public int shopOrderedItemId;

    public ItemRefundedRequest() {}

    public ItemRefundedRequest(Integer userId, int shopOrderedItemId) {
        this.userId = userId;
        //this.shopId = shopId;
        this.shopOrderedItemId = shopOrderedItemId;
    }
}
