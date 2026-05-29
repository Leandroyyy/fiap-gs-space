package br.com.sylo.sylo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record IotDeviceRequestDTO(
    @NotNull(message = "ID do talhão é obrigatório")
    Long fieldId,

    @NotBlank(message = "Nome do dispositivo é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String name,

    @NotBlank(message = "Tipo do dispositivo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    String deviceType,

    @Size(max = 100, message = "Número de série deve ter no máximo 100 caracteres")
    String serialNumber,

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    String status,

    BigDecimal latitude,
    BigDecimal longitude
) {}
