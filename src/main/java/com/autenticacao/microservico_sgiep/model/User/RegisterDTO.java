package com.autenticacao.microservico_sgiep.model.User;

public record RegisterDTO(String login, String password, UserRole role) {
}
