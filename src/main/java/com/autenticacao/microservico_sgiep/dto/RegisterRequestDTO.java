package com.autenticacao.microservico_sgiep.dto;

public record RegisterRequestDTO(String name, String email, String password, String role, String phone, String address) { }
