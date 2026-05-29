package br.com.sylo.sylo.service;

import br.com.sylo.sylo.dto.FarmRequestDTO;
import br.com.sylo.sylo.dto.FarmResponseDTO;
import br.com.sylo.sylo.entity.Farm;
import br.com.sylo.sylo.repository.FarmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Transactional(readOnly = true)
    public List<FarmResponseDTO> findAll() {
        return farmRepository.findAll()
                .stream()
                .map(FarmResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public FarmResponseDTO findById(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada com id: " + id));
        return FarmResponseDTO.fromEntity(farm);
    }

    @Transactional
    public FarmResponseDTO create(FarmRequestDTO dto) {
        Farm farm = new Farm();
        farm.setName(dto.name());
        farm.setDescription(dto.description());
        farm.setCity(dto.city());
        farm.setState(dto.state());
        farm.setLatitude(dto.latitude());
        farm.setLongitude(dto.longitude());

        Farm saved = farmRepository.save(farm);
        return FarmResponseDTO.fromEntity(saved);
    }

    @Transactional
    public FarmResponseDTO update(Long id, FarmRequestDTO dto) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada com id: " + id));

        farm.setName(dto.name());
        farm.setDescription(dto.description());
        farm.setCity(dto.city());
        farm.setState(dto.state());
        farm.setLatitude(dto.latitude());
        farm.setLongitude(dto.longitude());

        Farm updated = farmRepository.save(farm);
        return FarmResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!farmRepository.existsById(id)) {
            throw new RuntimeException("Fazenda não encontrada com id: " + id);
        }
        farmRepository.deleteById(id);
    }
}
