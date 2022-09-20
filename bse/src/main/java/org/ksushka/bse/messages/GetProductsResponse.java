package org.ksushka.bse.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.ksushka.bse.model.Product;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetProductsResponse {
    public String status;
    public List<Product> products;
    public String error;

    public static GetProductsResponse ok(List<Product> products) {
        GetProductsResponse r = new GetProductsResponse();
        r.status = "ok";
        r.products = products;
        return r;
    }
    public static GetProductsResponse error(String message) {
        GetProductsResponse r = new GetProductsResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
