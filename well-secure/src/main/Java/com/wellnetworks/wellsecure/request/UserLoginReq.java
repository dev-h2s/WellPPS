package com.wellnetworks.wellsecure.request;
// 로그인 할때 유저이름(id여야하지않나)와 패스워드
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter

public class UserLoginReq {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public UserLoginReq() {
    }

    public UserLoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
