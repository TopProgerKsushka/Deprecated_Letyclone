package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    public String status;
    public int userId;
    public String error;

    public static RegisterResponse ok(int userId) {
        RegisterResponse r = new RegisterResponse();
        r.status = "ok";
        r.userId = userId;
        return r;
    }

    public static RegisterResponse error(String message) {
        RegisterResponse r = new RegisterResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
