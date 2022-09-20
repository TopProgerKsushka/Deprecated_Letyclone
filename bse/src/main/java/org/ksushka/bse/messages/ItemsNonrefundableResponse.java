package org.ksushka.bse.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemsNonrefundableResponse {
    public String status;
    public String error;

    public static ItemsNonrefundableResponse ok() {
        ItemsNonrefundableResponse r = new ItemsNonrefundableResponse();
        r.status = "ok";
        return r;
    }

    public static ItemsNonrefundableResponse error(String message) {
        ItemsNonrefundableResponse r = new ItemsNonrefundableResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
