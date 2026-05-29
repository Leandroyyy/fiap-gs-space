package br.com.sylo.sylo.service;

import br.com.sylo.sylo.dto.AlertRequestDTO;
import br.com.sylo.sylo.dto.AlertResponseDTO;
import br.com.sylo.sylo.entity.Alert;
import br.com.sylo.sylo.entity.Field;
import br.com.sylo.sylo.repository.AlertRepository;
import br.com.sylo.sylo.repository.FieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final FieldRepository fieldRepository;

    public AlertService(AlertRepository alertRepository, FieldRepository fieldRepository) {
        this.alertRepository = alertRepository;
        this.fieldRepository = fieldRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> findAll() {
        return alertRepository.findAll()
                .stream()
                .map(AlertResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlertResponseDTO findById(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado com id: " + id));
        return AlertResponseDTO.fromEntity(alert);
    }

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> findByFieldId(Long fieldId) {
        return alertRepository.findByFieldId(fieldId)
                .stream()
                .map(AlertResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> findByStatus(String status) {
        return alertRepository.findByStatus(status)
                .stream()
                .map(AlertResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public AlertResponseDTO create(AlertRequestDTO dto) {
        Field field = fieldRepository.findById(dto.fieldId())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + dto.fieldId()));

        Alert alert = new Alert();
        alert.setField(field);
        alert.setFieldCropId(dto.fieldCropId());
        alert.setAlertType(dto.alertType());
        alert.setSeverity(dto.severity());
        alert.setMessage(dto.message());
        alert.setStatus(dto.status() != null ? dto.status() : "ACTIVE");

        Alert saved = alertRepository.save(alert);
        return AlertResponseDTO.fromEntity(saved);
    }

    @Transactional
    public AlertResponseDTO update(Long id, AlertRequestDTO dto) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado com id: " + id));

        Field field = fieldRepository.findById(dto.fieldId())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + dto.fieldId()));

        alert.setField(field);
        alert.setFieldCropId(dto.fieldCropId());
        alert.setAlertType(dto.alertType());
        alert.setSeverity(dto.severity());
        alert.setMessage(dto.message());
        alert.setStatus(dto.status());

        // Se o status mudou para RESOLVED, registrar o timestamp
        if ("RESOLVED".equalsIgnoreCase(dto.status())) {
            alert.setResolvedAt(LocalDateTime.now());
        }

        Alert updated = alertRepository.save(alert);
        return AlertResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!alertRepository.existsById(id)) {
            throw new RuntimeException("Alerta não encontrado com id: " + id);
        }
        alertRepository.deleteById(id);
    }
}
