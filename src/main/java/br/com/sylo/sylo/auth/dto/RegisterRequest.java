package br.com.sylo.sylo.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @NotBlank @Email(message = "E-mail inválido")
        String email,

        @NotBlank
        @Size(min = 8, max = 72, message = "Senha deve ter entre 8 e 72 caracteres")
        @Pattern(
            regexp  = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$",
            message = "Senha deve ter ao menos 1 maiúscula, 1 número e 1 caractere especial"
        )
        String password

) {}