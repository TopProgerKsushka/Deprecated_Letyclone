package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    public String status;
    public int userId;
    public String error;

    public static LoginResponse ok(int userId) {
        LoginResponse r = new LoginResponse();
        r.status = "ok";
        r.userId = userId;
        return r;
    }

    public static LoginResponse error(String message) {
        LoginResponse r = new LoginResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}

