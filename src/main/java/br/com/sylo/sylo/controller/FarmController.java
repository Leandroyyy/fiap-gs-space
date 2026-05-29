package br.com.sylo.sylo.controller;

import br.com.sylo.sylo.dto.FarmRequestDTO;
import br.com.sylo.sylo.dto.FarmResponseDTO;
import br.com.sylo.sylo.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@Tag(name = "Fazendas", description = "Gerenciamento de fazendas")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as fazendas")
    public ResponseEntity<List<FarmResponseDTO>> findAll() {
        return ResponseEntity.ok(farmService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fazenda por ID")
    public ResponseEntity<FarmResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(farmService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova fazenda")
    public ResponseEntity<FarmResponseDTO> create(@Valid @RequestBody FarmRequestDTO dto) {
        FarmResponseDTO created = farmService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fazenda existente")
    public ResponseEntity<FarmResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FarmRequestDTO dto) {
        return ResponseEntity.ok(farmService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fazenda")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
