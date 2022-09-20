package org.ksushka.bse.messages;

public class RefundRequest {

    public Integer lcUserId;
    public int itemId;

    public RefundRequest() {}

    public RefundRequest(Integer lcUserId, int itemId) {
        this.lcUserId = lcUserId;
        this.itemId = itemId;
    }
}
