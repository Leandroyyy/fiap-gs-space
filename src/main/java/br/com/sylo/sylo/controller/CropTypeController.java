package br.com.sylo.sylo.controller;

import br.com.sylo.sylo.dto.CropTypeRequestDTO;
import br.com.sylo.sylo.dto.CropTypeResponseDTO;
import br.com.sylo.sylo.service.CropTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crop-types")
@Tag(name = "Tipos de Cultura", description = "Gerenciamento de tipos de cultura agrícola")
public class CropTypeController {

    private final CropTypeService cropTypeService;

    public CropTypeController(CropTypeService cropTypeService) {
        this.cropTypeService = cropTypeService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de cultura")
    public ResponseEntity<List<CropTypeResponseDTO>> findAll() {
        return ResponseEntity.ok(cropTypeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de cultura por ID")
    public ResponseEntity<CropTypeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cropTypeService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo tipo de cultura")
    public ResponseEntity<CropTypeResponseDTO> create(@Valid @RequestBody CropTypeRequestDTO dto) {
        CropTypeResponseDTO created = cropTypeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de cultura existente")
    public ResponseEntity<CropTypeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CropTypeRequestDTO dto) {
        return ResponseEntity.ok(cropTypeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tipo de cultura")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cropTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
