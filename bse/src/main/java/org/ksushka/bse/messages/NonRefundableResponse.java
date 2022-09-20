package org.ksushka.bse.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NonRefundableResponse {
    public String status;
    public String error;
    public Integer orderId;

    public static NonRefundableResponse ok(Integer orderId) {
        NonRefundableResponse r = new NonRefundableResponse();
        r.status = "ok";
        r.orderId = orderId;
        return r;
    }
    public static NonRefundableResponse error(String message) {
        NonRefundableResponse r = new NonRefundableResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
