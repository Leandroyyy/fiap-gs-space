package br.com.sylo.sylo.service;

import br.com.sylo.sylo.dto.IotDeviceRequestDTO;
import br.com.sylo.sylo.dto.IotDeviceResponseDTO;
import br.com.sylo.sylo.entity.Field;
import br.com.sylo.sylo.entity.IotDevice;
import br.com.sylo.sylo.repository.FieldRepository;
import br.com.sylo.sylo.repository.IotDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IotDeviceService {

    private final IotDeviceRepository iotDeviceRepository;
    private final FieldRepository fieldRepository;

    public IotDeviceService(IotDeviceRepository iotDeviceRepository, FieldRepository fieldRepository) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.fieldRepository = fieldRepository;
    }

    @Transactional(readOnly = true)
    public List<IotDeviceResponseDTO> findAll() {
        return iotDeviceRepository.findAll()
                .stream()
                .map(IotDeviceResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public IotDeviceResponseDTO findById(Long id) {
        IotDevice device = iotDeviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo IoT não encontrado com id: " + id));
        return IotDeviceResponseDTO.fromEntity(device);
    }

    @Transactional(readOnly = true)
    public List<IotDeviceResponseDTO> findByFieldId(Long fieldId) {
        return iotDeviceRepository.findByFieldId(fieldId)
                .stream()
                .map(IotDeviceResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public IotDeviceResponseDTO create(IotDeviceRequestDTO dto) {
        Field field = fieldRepository.findById(dto.fieldId())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + dto.fieldId()));

        IotDevice device = new IotDevice();
        device.setField(field);
        device.setName(dto.name());
        device.setDeviceType(dto.deviceType());
        device.setSerialNumber(dto.serialNumber());
        device.setStatus(dto.status() != null ? dto.status() : "ONLINE");
        device.setLatitude(dto.latitude());
        device.setLongitude(dto.longitude());

        IotDevice saved = iotDeviceRepository.save(device);
        return IotDeviceResponseDTO.fromEntity(saved);
    }

    @Transactional
    public IotDeviceResponseDTO update(Long id, IotDeviceRequestDTO dto) {
        IotDevice device = iotDeviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo IoT não encontrado com id: " + id));

        Field field = fieldRepository.findById(dto.fieldId())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + dto.fieldId()));

        device.setField(field);
        device.setName(dto.name());
        device.setDeviceType(dto.deviceType());
        device.setSerialNumber(dto.serialNumber());
        device.setStatus(dto.status());
        device.setLatitude(dto.latitude());
        device.setLongitude(dto.longitude());

        IotDevice updated = iotDeviceRepository.save(device);
        return IotDeviceResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!iotDeviceRepository.existsById(id)) {
            throw new RuntimeException("Dispositivo IoT não encontrado com id: " + id);
        }
        iotDeviceRepository.deleteById(id);
    }
}
