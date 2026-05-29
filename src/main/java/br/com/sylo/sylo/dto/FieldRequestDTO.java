package br.com.sylo.sylo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record FieldRequestDTO(
    @NotNull(message = "ID da fazenda é obrigatório")
    Long farmId,

    @NotBlank(message = "Nome do talhão é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String name,

    BigDecimal areaHectares,
    BigDecimal latitude,
    BigDecimal longitude,

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    String status
) {}
