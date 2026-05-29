package br.com.sylo.sylo.dto;

import br.com.sylo.sylo.entity.Alert;
import java.time.LocalDateTime;

public record AlertResponseDTO(
    Long id,
    Long fieldId,
    String fieldName,
    Long fieldCropId,
    String alertType,
    String severity,
    String message,
    String status,
    LocalDateTime createdAt,
    LocalDateTime resolvedAt
) {
    public static AlertResponseDTO fromEntity(Alert alert) {
        return new AlertResponseDTO(
            alert.getId(),
            alert.getField().getId(),
            alert.getField().getName(),
            alert.getFieldCropId(),
            alert.getAlertType(),
            alert.getSeverity(),
            alert.getMessage(),
            alert.getStatus(),
            alert.getCreatedAt(),
            alert.getResolvedAt()
        );
    }
}
