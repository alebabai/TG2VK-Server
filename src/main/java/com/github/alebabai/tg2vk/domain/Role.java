package com.github.alebabai.tg2vk.domain;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
