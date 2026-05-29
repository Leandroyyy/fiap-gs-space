package br.com.sylo.sylo.dto;

import br.com.sylo.sylo.entity.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FieldResponseDTO(
    Long id,
    Long farmId,
    String farmName,
    String name,
    BigDecimal areaHectares,
    BigDecimal latitude,
    BigDecimal longitude,
    String status,
    LocalDateTime createdAt
) {
    public static FieldResponseDTO fromEntity(Field field) {
        return new FieldResponseDTO(
            field.getId(),
            field.getFarm().getId(),
            field.getFarm().getName(),
            field.getName(),
            field.getAreaHectares(),
            field.getLatitude(),
            field.getLongitude(),
            field.getStatus(),
            field.getCreatedAt()
        );
    }
}
