package com.wellnetworks.wellcore.java.domain;

public enum Role {
    ROLE_EMPLOYEE("EMPLOYEE"),
    ROLE_ADMIN("ADMIN"),
    ROLE_PARTNER("PARTNER");
    // "USER", "ADMIN"
    private String value;

    // Constructor
    Role(String value) {
        this.value = value;
    }

    // GetValue
    public String getValue() {
        return this.value;
    }

}
