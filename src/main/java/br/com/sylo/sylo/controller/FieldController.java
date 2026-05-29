package br.com.sylo.sylo.controller;

import br.com.sylo.sylo.dto.FieldRequestDTO;
import br.com.sylo.sylo.dto.FieldResponseDTO;
import br.com.sylo.sylo.service.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@Tag(name = "Talhões", description = "Gerenciamento de talhões/campos")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os talhões")
    public ResponseEntity<List<FieldResponseDTO>> findAll() {
        return ResponseEntity.ok(fieldService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar talhão por ID")
    public ResponseEntity<FieldResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fieldService.findById(id));
    }

    @GetMapping("/farm/{farmId}")
    @Operation(summary = "Listar talhões por fazenda")
    public ResponseEntity<List<FieldResponseDTO>> findByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(fieldService.findByFarmId(farmId));
    }

    @PostMapping
    @Operation(summary = "Criar novo talhão")
    public ResponseEntity<FieldResponseDTO> create(@Valid @RequestBody FieldRequestDTO dto) {
        FieldResponseDTO created = fieldService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar talhão existente")
    public ResponseEntity<FieldResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FieldRequestDTO dto) {
        return ResponseEntity.ok(fieldService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir talhão")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
