package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponse {
    public String status;
    public String error;

    public static GetUserResponse ok() {
        GetUserResponse r = new GetUserResponse();
        r.status = "ok";
        return r;
    }

    public static GetUserResponse error(String message) {
        GetUserResponse r = new GetUserResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
