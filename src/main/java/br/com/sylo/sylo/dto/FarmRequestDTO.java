package br.com.sylo.sylo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record FarmRequestDTO(
    @NotBlank(message = "Nome da fazenda é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    String name,

    String description,

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    String city,

    @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
    String state,

    BigDecimal latitude,
    BigDecimal longitude
) {}
