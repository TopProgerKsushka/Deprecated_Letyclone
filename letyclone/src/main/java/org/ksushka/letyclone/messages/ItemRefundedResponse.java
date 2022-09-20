package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemRefundedResponse {
    public String status;
    public String error;

    public static ItemRefundedResponse ok() {
        ItemRefundedResponse r = new ItemRefundedResponse();
        r.status = "ok";
        return r;
    }

    public static ItemRefundedResponse error(String message) {
        ItemRefundedResponse r = new ItemRefundedResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
