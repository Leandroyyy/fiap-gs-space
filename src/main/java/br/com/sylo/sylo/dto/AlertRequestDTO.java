package br.com.sylo.sylo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlertRequestDTO(
    @NotNull(message = "ID do talhão é obrigatório")
    Long fieldId,

    Long fieldCropId,

    @NotBlank(message = "Tipo do alerta é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    String alertType,

    @NotBlank(message = "Severidade é obrigatória")
    @Size(max = 20, message = "Severidade deve ter no máximo 20 caracteres")
    String severity,

    String message,

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    String status
) {}
