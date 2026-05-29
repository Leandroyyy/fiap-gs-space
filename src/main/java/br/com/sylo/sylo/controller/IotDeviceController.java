package br.com.sylo.sylo.controller;

import br.com.sylo.sylo.dto.IotDeviceRequestDTO;
import br.com.sylo.sylo.dto.IotDeviceResponseDTO;
import br.com.sylo.sylo.service.IotDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iot-devices")
@Tag(name = "Dispositivos IoT", description = "Gerenciamento de dispositivos IoT no campo")
public class IotDeviceController {

    private final IotDeviceService iotDeviceService;

    public IotDeviceController(IotDeviceService iotDeviceService) {
        this.iotDeviceService = iotDeviceService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os dispositivos IoT")
    public ResponseEntity<List<IotDeviceResponseDTO>> findAll() {
        return ResponseEntity.ok(iotDeviceService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dispositivo IoT por ID")
    public ResponseEntity<IotDeviceResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(iotDeviceService.findById(id));
    }

    @GetMapping("/field/{fieldId}")
    @Operation(summary = "Listar dispositivos IoT por talhão")
    public ResponseEntity<List<IotDeviceResponseDTO>> findByFieldId(@PathVariable Long fieldId) {
        return ResponseEntity.ok(iotDeviceService.findByFieldId(fieldId));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo dispositivo IoT")
    public ResponseEntity<IotDeviceResponseDTO> create(@Valid @RequestBody IotDeviceRequestDTO dto) {
        IotDeviceResponseDTO created = iotDeviceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dispositivo IoT existente")
    public ResponseEntity<IotDeviceResponseDTO> update(@PathVariable Long id, @Valid @RequestBody IotDeviceRequestDTO dto) {
        return ResponseEntity.ok(iotDeviceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dispositivo IoT")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iotDeviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
