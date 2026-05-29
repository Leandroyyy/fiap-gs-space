package br.com.sylo.sylo.dto;

import br.com.sylo.sylo.entity.Farm;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FarmResponseDTO(
    Long id,
    String name,
    String description,
    String city,
    String state,
    BigDecimal latitude,
    BigDecimal longitude,
    LocalDateTime createdAt
) {
    public static FarmResponseDTO fromEntity(Farm farm) {
        return new FarmResponseDTO(
            farm.getId(),
            farm.getName(),
            farm.getDescription(),
            farm.getCity(),
            farm.getState(),
            farm.getLatitude(),
            farm.getLongitude(),
            farm.getCreatedAt()
        );
    }
}
