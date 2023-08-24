package com.store.model.users;

public enum RoleEnum {
    USER("user"),
    MANAGER("manager"),
    ADMIN("admin");

    private final String role;
    RoleEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
