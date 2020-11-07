package com.company.demo.model;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED");

    private String name;

    UserStatusEnum(String name) {
        this.name = name;
    }
}
