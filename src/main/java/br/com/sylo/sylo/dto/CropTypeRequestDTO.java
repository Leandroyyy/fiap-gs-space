package br.com.sylo.sylo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CropTypeRequestDTO(
    @NotBlank(message = "Nome do tipo de cultura é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String name,

    String description,
    BigDecimal idealMinSoilMoisture,
    BigDecimal idealMaxSoilMoisture,
    BigDecimal idealMinTemperature,
    BigDecimal idealMaxTemperature,
    BigDecimal idealMinNdvi,
    BigDecimal idealMaxNdvi
) {}
