package br.com.sylo.sylo.auth.controller;

import br.com.sylo.sylo.auth.dto.AuthResponse;
import br.com.sylo.sylo.auth.dto.LoginRequest;
import br.com.sylo.sylo.auth.dto.RegisterRequest;
import br.com.sylo.sylo.auth.service.UserService;
import br.com.sylo.sylo.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthController {

    private final UserService           userService;
    private final AuthenticationManager authManager;
    private final JwtUtil               jwtUtil;

    public AuthController(UserService userService,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil     = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar usuário com senha criptografada (BCrypt)")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            userService.register(req.name(), req.email(), req.password());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
        }
        String token = jwtUtil.generateToken(req.email());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Bearer",
                        jwtUtil.getExpirationMs() / 1000,
                        req.email(), req.name()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login — retorna token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorBody("Credenciais inválidas"));
        }
        String token = jwtUtil.generateToken(req.email());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer",
                jwtUtil.getExpirationMs() / 1000,
                req.email(), userService.nameOf(req.email())));
    }

    record ErrorBody(String message) {}
}