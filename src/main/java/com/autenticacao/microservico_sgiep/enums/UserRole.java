package com.autenticacao.microservico_sgiep.enums;

public enum UserRole {
    ADMIN("admin"),
    PROFESSOR("professor"),
    MANAGER("manager"),
    CITIZEN("citizen");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
