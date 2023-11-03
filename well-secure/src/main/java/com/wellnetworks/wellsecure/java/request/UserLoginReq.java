package com.wellnetworks.wellsecure.java.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginReq {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
