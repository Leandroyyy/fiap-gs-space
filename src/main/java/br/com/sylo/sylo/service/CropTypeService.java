package br.com.sylo.sylo.service;

import br.com.sylo.sylo.dto.CropTypeRequestDTO;
import br.com.sylo.sylo.dto.CropTypeResponseDTO;
import br.com.sylo.sylo.entity.CropType;
import br.com.sylo.sylo.repository.CropTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CropTypeService {

    private final CropTypeRepository cropTypeRepository;

    public CropTypeService(CropTypeRepository cropTypeRepository) {
        this.cropTypeRepository = cropTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<CropTypeResponseDTO> findAll() {
        return cropTypeRepository.findAll()
                .stream()
                .map(CropTypeResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public CropTypeResponseDTO findById(Long id) {
        CropType cropType = cropTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cultura não encontrado com id: " + id));
        return CropTypeResponseDTO.fromEntity(cropType);
    }

    @Transactional
    public CropTypeResponseDTO create(CropTypeRequestDTO dto) {
        CropType cropType = new CropType();
        cropType.setName(dto.name());
        cropType.setDescription(dto.description());
        cropType.setIdealMinSoilMoisture(dto.idealMinSoilMoisture());
        cropType.setIdealMaxSoilMoisture(dto.idealMaxSoilMoisture());
        cropType.setIdealMinTemperature(dto.idealMinTemperature());
        cropType.setIdealMaxTemperature(dto.idealMaxTemperature());
        cropType.setIdealMinNdvi(dto.idealMinNdvi());
        cropType.setIdealMaxNdvi(dto.idealMaxNdvi());

        CropType saved = cropTypeRepository.save(cropType);
        return CropTypeResponseDTO.fromEntity(saved);
    }

    @Transactional
    public CropTypeResponseDTO update(Long id, CropTypeRequestDTO dto) {
        CropType cropType = cropTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cultura não encontrado com id: " + id));

        cropType.setName(dto.name());
        cropType.setDescription(dto.description());
        cropType.setIdealMinSoilMoisture(dto.idealMinSoilMoisture());
        cropType.setIdealMaxSoilMoisture(dto.idealMaxSoilMoisture());
        cropType.setIdealMinTemperature(dto.idealMinTemperature());
        cropType.setIdealMaxTemperature(dto.idealMaxTemperature());
        cropType.setIdealMinNdvi(dto.idealMinNdvi());
        cropType.setIdealMaxNdvi(dto.idealMaxNdvi());

        CropType updated = cropTypeRepository.save(cropType);
        return CropTypeResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!cropTypeRepository.existsById(id)) {
            throw new RuntimeException("Tipo de cultura não encontrado com id: " + id);
        }
        cropTypeRepository.deleteById(id);
    }
}
