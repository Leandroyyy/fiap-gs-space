package br.com.sylo.sylo.controller;

import br.com.sylo.sylo.dto.AlertRequestDTO;
import br.com.sylo.sylo.dto.AlertResponseDTO;
import br.com.sylo.sylo.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alertas", description = "Gerenciamento de alertas do sistema")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alertas")
    public ResponseEntity<List<AlertResponseDTO>> findAll() {
        return ResponseEntity.ok(alertService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID")
    public ResponseEntity<AlertResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.findById(id));
    }

    @GetMapping("/field/{fieldId}")
    @Operation(summary = "Listar alertas por talhão")
    public ResponseEntity<List<AlertResponseDTO>> findByFieldId(@PathVariable Long fieldId) {
        return ResponseEntity.ok(alertService.findByFieldId(fieldId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar alertas por status")
    public ResponseEntity<List<AlertResponseDTO>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(alertService.findByStatus(status));
    }

    @PostMapping
    @Operation(summary = "Criar novo alerta")
    public ResponseEntity<AlertResponseDTO> create(@Valid @RequestBody AlertRequestDTO dto) {
        AlertResponseDTO created = alertService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta existente")
    public ResponseEntity<AlertResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AlertRequestDTO dto) {
        return ResponseEntity.ok(alertService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alerta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
