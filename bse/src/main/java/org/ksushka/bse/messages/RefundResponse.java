package org.ksushka.bse.messages;

import org.ksushka.bse.model.OrderedItem;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundResponse {
    public String status;
    public OrderedItem item;
    public String error;
    public Integer orderId;

    public static RefundResponse ok(OrderedItem item, Integer orderId) {
        RefundResponse r = new RefundResponse();
        r.status = "ok";
        r.item = item;
        r.orderId = orderId;
        return r;
    }
    public static RefundResponse error(String message) {
        RefundResponse r = new RefundResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
