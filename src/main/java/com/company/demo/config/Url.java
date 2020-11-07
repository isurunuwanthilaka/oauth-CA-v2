package com.company.demo.config;

import lombok.Getter;

public enum Url {

    HOME("/home"),
    LOGOUT("/logout"),
    LOGOUT_SUCCESS("/logout-success"),
    JWT_VALIDATE("/jwt/validate");

    @Getter
    private String url;
    private String version = "v1";

    Url(String url) {
        this.url = prefixVersion(url);
    }

    private String prefixVersion(String url) {
        return String.format("/%s%s", this.version, url);
    }
}
