package com.example.demo.model;

public enum RoleEnum {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMINISTRATOR");

    String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
