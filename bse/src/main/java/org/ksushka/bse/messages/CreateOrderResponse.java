package org.ksushka.bse.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderResponse {
    public String status;
    public Integer id;
    public String error;

    public static CreateOrderResponse ok(Integer id) {
        CreateOrderResponse r = new CreateOrderResponse();
        r.status = "ok";
        r.id = id;
        return r;
    }
    public static CreateOrderResponse error(String message) {
        CreateOrderResponse r = new CreateOrderResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}

