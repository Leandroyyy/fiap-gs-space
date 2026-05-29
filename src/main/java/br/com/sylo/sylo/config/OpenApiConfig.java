package br.com.sylo.sylo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sylo - Smart Farming Platform API")
                        .version("1.0.0")
                        .description("""
                                API REST da plataforma Sylo para agricultura inteligente.
                                
                                O Sylo integra dados de sensores IoT, observações de satélite e regras de decisão
                                para automatizar o gerenciamento agrícola, gerar alertas e otimizar a produção.
                                
                                **Principais funcionalidades:**
                                - Gerenciamento de fazendas e talhões
                                - Cadastro de tipos de cultura com parâmetros ideais
                                - Monitoramento de dispositivos IoT
                                - Sistema de alertas inteligentes
                                """)
                        .contact(new Contact()
                                .name("Equipe Sylo")
                                .email("contato@sylo.com.br"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")
                ));
    }
}
