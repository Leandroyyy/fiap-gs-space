package br.com.sylo.sylo.auth.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositório de usuários em memória (mock).
 * Substitua pelo JPA UserRepository quando quiser persistência real.
 */
@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;

    // email → hash da senha
    private final Map<String, String> nameByEmail = new ConcurrentHashMap<>();
    private final Map<String, String> hashByEmail = new ConcurrentHashMap<>();

    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
        // Usuários seed para demo
        register("Admin Sylo",   "admin@sylo.com", "Admin@123");
        register("Usuário Demo", "user@sylo.com",  "User@1234");
    }

    /** Cadastra usuário hasheando a senha com BCrypt. */
    public void register(String name, String email, String rawPassword) {
        if (hashByEmail.containsKey(email.toLowerCase()))
            throw new IllegalArgumentException("E-mail já cadastrado");
        hashByEmail.put(email.toLowerCase(), encoder.encode(rawPassword));
        nameByEmail.put(email.toLowerCase(), name);
    }

    public String nameOf(String email) {
        return nameByEmail.getOrDefault(email.toLowerCase(), "");
    }

    /** Chamado pelo Spring Security para verificar as credenciais. */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String hash = hashByEmail.get(email.toLowerCase());
        if (hash == null)
            throw new UsernameNotFoundException("Credenciais inválidas");
        return User.withUsername(email.toLowerCase())
                .password(hash)
                .authorities(List.of())
                .build();
    }
}