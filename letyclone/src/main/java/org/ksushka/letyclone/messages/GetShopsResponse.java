package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.ksushka.letyclone.model.Shop;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetShopsResponse {
    public String status;
    public String error;
    public List<Shop> shops;

    public static GetShopsResponse ok(List<Shop> shops) {
        GetShopsResponse r = new GetShopsResponse();
        r.status = "ok";
        r.shops = shops;
        return r;
    }

    public static GetShopsResponse error(String message) {
        GetShopsResponse r = new GetShopsResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}


