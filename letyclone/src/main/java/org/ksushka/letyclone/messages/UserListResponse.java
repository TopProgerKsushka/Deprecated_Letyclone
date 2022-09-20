package org.ksushka.letyclone.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserListResponse {
    public List<String> usernames;
    public UserListResponse() {}
    public UserListResponse(List<String> usernames) {
        this.usernames = usernames;
    }
}
