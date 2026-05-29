package br.com.sylo.sylo.dto;

import br.com.sylo.sylo.entity.CropType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CropTypeResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal idealMinSoilMoisture,
    BigDecimal idealMaxSoilMoisture,
    BigDecimal idealMinTemperature,
    BigDecimal idealMaxTemperature,
    BigDecimal idealMinNdvi,
    BigDecimal idealMaxNdvi,
    LocalDateTime createdAt
) {
    public static CropTypeResponseDTO fromEntity(CropType cropType) {
        return new CropTypeResponseDTO(
            cropType.getId(),
            cropType.getName(),
            cropType.getDescription(),
            cropType.getIdealMinSoilMoisture(),
            cropType.getIdealMaxSoilMoisture(),
            cropType.getIdealMinTemperature(),
            cropType.getIdealMaxTemperature(),
            cropType.getIdealMinNdvi(),
            cropType.getIdealMaxNdvi(),
            cropType.getCreatedAt()
        );
    }
}
