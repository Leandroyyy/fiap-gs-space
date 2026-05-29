package br.com.sylo.sylo.service;

import br.com.sylo.sylo.dto.FieldRequestDTO;
import br.com.sylo.sylo.dto.FieldResponseDTO;
import br.com.sylo.sylo.entity.Farm;
import br.com.sylo.sylo.entity.Field;
import br.com.sylo.sylo.repository.FarmRepository;
import br.com.sylo.sylo.repository.FieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldService(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Transactional(readOnly = true)
    public List<FieldResponseDTO> findAll() {
        return fieldRepository.findAll()
                .stream()
                .map(FieldResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public FieldResponseDTO findById(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + id));
        return FieldResponseDTO.fromEntity(field);
    }

    @Transactional(readOnly = true)
    public List<FieldResponseDTO> findByFarmId(Long farmId) {
        return fieldRepository.findByFarmId(farmId)
                .stream()
                .map(FieldResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public FieldResponseDTO create(FieldRequestDTO dto) {
        Farm farm = farmRepository.findById(dto.farmId())
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada com id: " + dto.farmId()));

        Field field = new Field();
        field.setFarm(farm);
        field.setName(dto.name());
        field.setAreaHectares(dto.areaHectares());
        field.setLatitude(dto.latitude());
        field.setLongitude(dto.longitude());
        field.setStatus(dto.status() != null ? dto.status() : "ACTIVE");

        Field saved = fieldRepository.save(field);
        return FieldResponseDTO.fromEntity(saved);
    }

    @Transactional
    public FieldResponseDTO update(Long id, FieldRequestDTO dto) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com id: " + id));

        Farm farm = farmRepository.findById(dto.farmId())
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada com id: " + dto.farmId()));

        field.setFarm(farm);
        field.setName(dto.name());
        field.setAreaHectares(dto.areaHectares());
        field.setLatitude(dto.latitude());
        field.setLongitude(dto.longitude());
        field.setStatus(dto.status());

        Field updated = fieldRepository.save(field);
        return FieldResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!fieldRepository.existsById(id)) {
            throw new RuntimeException("Talhão não encontrado com id: " + id);
        }
        fieldRepository.deleteById(id);
    }
}
