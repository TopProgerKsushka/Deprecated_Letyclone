package org.ksushka.letyclone.messages;

import java.util.List;

public class UserCreatedOrderRequest {
    //public int shopId;
    public int userId;
    public List<Item> items;

    public UserCreatedOrderRequest() {}

    public UserCreatedOrderRequest(int userId, List<Item> items) {
        //this.shopId = shopId;
        this.userId = userId;
        this.items = items;
    }

    public static class Item {
        public int itemId;
        public double cashback;
        public boolean canBeRefunded;
        public boolean refunded;

        public Item() {}

        public Item(int itemId, double cashback, boolean canBeRefunded, boolean refunded) {
            this.itemId = itemId;
            this.cashback = cashback;
            this.canBeRefunded = canBeRefunded;
            this.refunded = refunded;
        }
    }
}
