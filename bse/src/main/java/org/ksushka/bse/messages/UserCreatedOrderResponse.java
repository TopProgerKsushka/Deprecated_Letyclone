package org.ksushka.bse.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreatedOrderResponse {
    public String status;
    public String error;

    public static UserCreatedOrderResponse ok() {
        UserCreatedOrderResponse r = new UserCreatedOrderResponse();
        r.status = "ok";
        return r;
    }

    public static UserCreatedOrderResponse error(String message) {
        UserCreatedOrderResponse r = new UserCreatedOrderResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
