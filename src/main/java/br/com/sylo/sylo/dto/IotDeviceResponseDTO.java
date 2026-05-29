package br.com.sylo.sylo.dto;

import br.com.sylo.sylo.entity.IotDevice;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IotDeviceResponseDTO(
    Long id,
    Long fieldId,
    String fieldName,
    String name,
    String deviceType,
    String serialNumber,
    String status,
    BigDecimal latitude,
    BigDecimal longitude,
    LocalDateTime createdAt
) {
    public static IotDeviceResponseDTO fromEntity(IotDevice device) {
        return new IotDeviceResponseDTO(
            device.getId(),
            device.getField().getId(),
            device.getField().getName(),
            device.getName(),
            device.getDeviceType(),
            device.getSerialNumber(),
            device.getStatus(),
            device.getLatitude(),
            device.getLongitude(),
            device.getCreatedAt()
        );
    }
}
