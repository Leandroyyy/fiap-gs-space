# 🌿 Sylo - Smart Farming Platform
 
**Plataforma de agricultura inteligente** que integra dados de sensores IoT, observações de satélite e regras de decisão para automatizar o gerenciamento agrícola, gerar alertas e otimizar a produção.
 
> Projeto desenvolvido como Global Solution para a FIAP.
 
---
 
## 📋 Índice
 
- [Sobre o Projeto](#-sobre-o-projeto)
- [Stack Tecnológica](#-stack-tecnológica)
- [Arquitetura](#-arquitetura)
- [Segurança](#-segurança)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Documentação Swagger](#-documentação-swagger)
- [Testes BDD](#-testes-bdd)
- [Banco de Dados](#-banco-de-dados)
- [Integrantes](#-integrantes)
---
 
## 🎯 Sobre o Projeto
 
O **Sylo** é uma solução backend para gestão agrícola inteligente que permite:
 
- **Gerenciar fazendas e talhões** com geolocalização
- **Cadastrar tipos de cultura** com parâmetros ideais de solo, temperatura e NDVI
- **Monitorar dispositivos IoT** (sensores de umidade, temperatura, estações meteorológicas)
- **Gerar e gerenciar alertas** automáticos baseados em condições das lavouras
- **Automatizar ações** de irrigação e manutenção de dispositivos
O sistema foi projetado para atender o agronegócio brasileiro, com dados de fazendas reais em SP e MG, culturas como soja, milho, café e alface, e integração com fontes de dados como Sentinel-2 e Landsat-8.
 
---
 
## 🛠 Stack Tecnológica
 
| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 3.4.5 | Framework backend |
| **Spring Security** | 6.4.5 | Autenticação e autorização |
| **Spring Data JPA** | 3.4.x | Persistência de dados |
| **Hibernate** | 6.6.x | ORM |
| **H2 Database** | 2.3.x | Banco de dados em memória |
| **JJWT** | 0.12.6 | Geração e validação de tokens JWT |
| **BCrypt** | — | Hash de senhas |
| **SpringDoc OpenAPI** | 2.8.8 | Documentação Swagger UI |
| **Bean Validation** | 3.1 | Validação de entrada |
| **Cucumber** | 7.22.0 | Testes BDD (Gherkin) |
| **JUnit 5** | 5.11.x | Framework de testes |
| **Maven** | 3.9.x | Build e dependências |
 
---
 
## 🏗 Arquitetura
 
O projeto segue a **arquitetura em camadas** do Spring Boot:
 
```
┌─────────────────────────────────────────────┐
│               HTTP Request                   │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│           Security Layer             │
│  JwtFilter — valida token em toda requisição │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│             Controller Layer                 │
│  (REST endpoints + Swagger annotations)      │
│  FarmController, FieldController, etc.       │
│  AuthController                      │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│              Service Layer                   │
│  (Lógica de negócio + Transações)            │
│  FarmService, FieldService, etc.             │
│  UserService                        │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│            Repository Layer                  │
│  (Spring Data JPA + Queries customizadas)    │
│  FarmRepository, FieldRepository, etc.       │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│              H2 Database                     │
│         (In-memory, SQL init)                │
└─────────────────────────────────────────────┘
```
 
---
 
## 🔒 Segurança
 
A aplicação implementa autenticação e proteção de dados seguindo boas práticas de segurança.
 
### Sistema de Login com Senha Criptografada
 
O endpoint `POST /auth/register` recebe a senha em texto plano e armazena **apenas o hash BCrypt** (custo 12). A senha original nunca é salva nem logada.
 
O endpoint `POST /auth/login` valida as credenciais via Spring Security, que compara o hash automaticamente. Em caso de sucesso, retorna um **token JWT** assinado com HMAC-SHA256, válido por 1 hora.
 
Todas as rotas `/api/**` exigem o token no cabeçalho:
```
Authorization: Bearer <token>
```
 
### Prática 1 — Validação de Entrada (Bean Validation)
 
Os DTOs de registro aplicam regras antes de qualquer lógica de negócio:
 
- `@Email` — rejeita e-mails com formato inválido
- `@Size(min=8, max=72)` — limita o tamanho da senha
- `@Pattern` — exige ao menos 1 letra maiúscula, 1 número e 1 caractere especial
Exemplo de senha rejeitada: `123456` → **400 Bad Request**
Exemplo de senha aceita: `Sylo@2026` → **201 Created**
 
### Prática 2 — Autenticação Stateless com JWT
 
Nenhuma sessão HTTP é criada no servidor. Cada requisição valida o token de forma independente via `JwtFilter`. Tokens expirados ou adulterados retornam **401 Unauthorized** com mensagem genérica — sem revelar detalhes internos.
 
### Usuários disponíveis para teste
 
| E-mail | Senha | Perfil |
|---|---|---|
| `admin@sylo.com` | `Admin@123` | Administrador |
| `user@sylo.com` | `User@1234` | Usuário comum |
 
### Arquivos adicionados
 
| Arquivo | Descrição |
|---|---|
| `config/SecurityConfig.java` | Configuração central do Spring Security |
| `security/JwtUtil.java` | Geração e validação de tokens JWT |
| `security/JwtFilter.java` | Filtro que intercepta e valida o token em toda requisição |
| `auth/dto/RegisterRequest.java` | DTO de registro com validações de senha |
| `auth/dto/LoginRequest.java` | DTO de login |
| `auth/dto/AuthResponse.java` | DTO de resposta com token JWT |
| `auth/service/UserService.java` | Serviço de usuários com hash BCrypt |
| `auth/controller/AuthController.java` | Endpoints `/auth/register` e `/auth/login` |
 
---
 
## 📂 Estrutura do Projeto
 
```
sylo/
├── docs/
│   ├── Sylo_API.postman_collection.json   # Collection Postman
│   ├── cucumber-report.html               # Relatório de testes HTML
│   ├── cucumber-report.json               # Relatório de testes JSON
├── scripts/
│   └── scriptu.sql                        # Script SQL original
├── src/
│   ├── main/
│   │   ├── java/br/com/sylo/sylo/
│   │   │   ├── SyloApplication.java       # Main class
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java     # Swagger config
│   │   │   │   └── SecurityConfig.java        
│   │   │   ├── security/                      
│   │   │   │   ├── JwtUtil.java               
│   │   │   │   └── JwtFilter.java             
│   │   │   ├── auth/                          
│   │   │   │   ├── controller/
│   │   │   │   │   └── AuthController.java    
│   │   │   │   ├── service/
│   │   │   │   │   └── UserService.java       
│   │   │   │   └── dto/
│   │   │   │       ├── RegisterRequest.java   
│   │   │   │       ├── LoginRequest.java      
│   │   │   │       └── AuthResponse.java      
│   │   │   ├── controller/
│   │   │   │   ├── FarmController.java
│   │   │   │   ├── FieldController.java
│   │   │   │   ├── CropTypeController.java
│   │   │   │   ├── IotDeviceController.java
│   │   │   │   └── AlertController.java
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   └── exception/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/
│       ├── java/br/com/sylo/sylo/
│       │   ├── CucumberRunnerTest.java
│       │   └── bdd/
│       │       ├── CucumberSpringConfig.java
│       │       └── CommonSteps.java
│       └── resources/
│           ├── features/
│           │   ├── farm.feature
│           │   ├── field.feature
│           │   ├── crop_type.feature
│           │   ├── iot_device.feature
│           │   ├── alert.feature
│           │   └── auth.feature               ← NOVO
│           ├── cucumber.properties
│           └── junit-platform.properties
└── pom.xml
```
 
---

## 🚀 Como Executar

### Pré-requisitos

- **Java 21** (JDK)
- **Maven 3.9+** (ou usar o wrapper `./mvnw` incluído)

### Rodando a aplicação

```bash
# Clonar o repositório
git clone https://github.com/Leandroyyy/fiap-gs-space.git
cd fiap-gs-space

# Executar a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em **http://localhost:8080**.

### Rodando os testes

```bash
# Executar todos os testes BDD
./mvnw test

# Executar apenas os testes Cucumber
./mvnw test -Dtest="CucumberRunnerTest"
```

---

## 🔗 Endpoints da API

O backend expõe **27 endpoints** organizados em 6 domínios:

### Autenticação (`/auth`)
 
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/register` | Cadastrar usuário com senha criptografada (BCrypt) |
| `POST` | `/auth/login` | Login (retorna token JWT) |

### Fazendas (`/api/farms`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/farms` | Listar todas as fazendas |
| `GET` | `/api/farms/{id}` | Buscar fazenda por ID |
| `POST` | `/api/farms` | Criar nova fazenda |
| `PUT` | `/api/farms/{id}` | Atualizar fazenda |
| `DELETE` | `/api/farms/{id}` | Excluir fazenda |

### Talhões (`/api/fields`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/fields` | Listar todos os talhões |
| `GET` | `/api/fields/{id}` | Buscar talhão por ID |
| `GET` | `/api/fields/farm/{farmId}` | Listar por fazenda |
| `POST` | `/api/fields` | Criar novo talhão |
| `PUT` | `/api/fields/{id}` | Atualizar talhão |
| `DELETE` | `/api/fields/{id}` | Excluir talhão |

### Tipos de Cultura (`/api/crop-types`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/crop-types` | Listar todos os tipos |
| `GET` | `/api/crop-types/{id}` | Buscar tipo por ID |
| `POST` | `/api/crop-types` | Criar novo tipo |
| `PUT` | `/api/crop-types/{id}` | Atualizar tipo |
| `DELETE` | `/api/crop-types/{id}` | Excluir tipo |

### Dispositivos IoT (`/api/iot-devices`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/iot-devices` | Listar todos |
| `GET` | `/api/iot-devices/{id}` | Buscar por ID |
| `GET` | `/api/iot-devices/field/{fieldId}` | Listar por talhão |
| `POST` | `/api/iot-devices` | Cadastrar dispositivo |
| `PUT` | `/api/iot-devices/{id}` | Atualizar dispositivo |
| `DELETE` | `/api/iot-devices/{id}` | Excluir dispositivo |

### Alertas (`/api/alerts`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/alerts` | Listar todos |
| `GET` | `/api/alerts/{id}` | Buscar por ID |
| `GET` | `/api/alerts/field/{fieldId}` | Listar por talhão |
| `GET` | `/api/alerts/status/{status}` | Listar por status |
| `POST` | `/api/alerts` | Criar alerta |
| `PUT` | `/api/alerts/{id}` | Atualizar alerta |
| `DELETE` | `/api/alerts/{id}` | Excluir alerta |

### Exemplos de Request

<details>
<summary><b>POST /api/farms</b> - Criar fazenda</summary>

```json
{
  "name": "Fazenda Boa Vista",
  "description": "Fazenda de soja e milho no interior de SP",
  "city": "Ribeirão Preto",
  "state": "SP",
  "latitude": -21.1767,
  "longitude": -47.8208
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Fazenda Boa Vista",
  "description": "Fazenda de soja e milho no interior de SP",
  "city": "Ribeirão Preto",
  "state": "SP",
  "latitude": -21.1767,
  "longitude": -47.8208,
  "createdAt": "2026-05-29T11:10:04.408582"
}
```
</details>

<details>
<summary><b>POST /api/alerts</b> - Criar alerta</summary>

```json
{
  "fieldId": 1,
  "fieldCropId": 1,
  "alertType": "LOW_MOISTURE",
  "severity": "WARNING",
  "message": "Umidade do solo abaixo do ideal para soja",
  "status": "ACTIVE"
}
```

**Response (201 Created):**
```json
{
  "id": 4,
  "fieldId": 1,
  "fieldName": "Talhão A1",
  "fieldCropId": 1,
  "alertType": "LOW_MOISTURE",
  "severity": "WARNING",
  "message": "Umidade do solo abaixo do ideal para soja",
  "status": "ACTIVE",
  "createdAt": "2026-05-29T11:30:00.000000",
  "resolvedAt": null
}
```
</details>

---

## 📖 Documentação Swagger

Com a aplicação rodando, acesse:

| URL | Descrição |
|---|---|
| http://localhost:8080/swagger-ui.html | Interface interativa Swagger UI |
| http://localhost:8080/api-docs | OpenAPI JSON spec |
| http://localhost:8080/h2-console | Console do banco H2 |

> Para o H2 Console: JDBC URL = `jdbc:h2:mem:sylodb`, User = `sa`, Password = *(vazio)*

Também disponibilizamos uma **Postman Collection** em `docs/Sylo_API.postman_collection.json` — basta importar no Postman ou Insomnia.

---

## 🧪 Testes BDD

Os testes E2E foram implementados com **Cucumber + Gherkin** seguindo a abordagem **BDD (Behavior-Driven Development)**.

Os cenários são executados a partir de `src/test/resources/features`, com as definições de passos em `src/test/java/br/com/sylo/sylo/bdd`.

Como a API agora exige **Bearer JWT** para os endpoints protegidos, os testes criam um usuário de teste dinamicamente via `/auth/register` e anexam o token `Authorization: Bearer ...` em todas as requisições `/api/*`.

### Cenários de Teste

São **27 cenários** distribuídos em 6 funcionalidades:

| Funcionalidade | Cenários | Arquivo |
|---|---|---|
| Fazendas | 6 | `farm.feature` |
| Talhões | 5 | `field.feature` |
| Tipos de Cultura | 4 | `crop_type.feature` |
| Dispositivos IoT | 4 | `iot_device.feature` |
| Alertas | 6 | `alert.feature` |
| Segurança | 2 | `auth.feature` |

### Exemplo de cenário Gherkin

```gherkin
# language: pt
Funcionalidade: Gerenciamento de Fazendas

  Cenário: Criar nova fazenda com dados válidos
    Dado que eu tenho os dados de uma nova fazenda:
      | name        | Fazenda Primavera               |
      | description | Fazenda de hortaliças orgânicas |
      | city        | Campinas                        |
      | state       | SP                              |
      | latitude    | -22.9099                        |
      | longitude   | -47.0626                        |
    Quando eu enviar uma requisição POST para "/api/farms"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "name" com valor "Fazenda Primavera"
    E a resposta deve conter o campo "id"
```

### Resultado da Execução

```
$ ./mvnw test -Dtest="CucumberRunnerTest"

Cenário: Listar todas as fazendas cadastradas            ✅ PASS
Cenário: Buscar fazenda por ID existente                 ✅ PASS
Cenário: Criar nova fazenda com dados válidos            ✅ PASS
Cenário: Criar fazenda sem nome deve falhar              ✅ PASS
Cenário: Atualizar fazenda existente                     ✅ PASS
Cenário: Buscar fazenda com ID inexistente               ✅ PASS
Cenário: Listar todos os talhões                         ✅ PASS
Cenário: Buscar talhões de uma fazenda específica        ✅ PASS
Cenário: Criar novo talhão vinculado a uma fazenda       ✅ PASS
Cenário: Criar talhão sem nome deve falhar               ✅ PASS
Cenário: Criar talhão com fazenda inexistente            ✅ PASS
Cenário: Listar todos os tipos de cultura                ✅ PASS
Cenário: Buscar tipo de cultura por ID                   ✅ PASS
Cenário: Criar novo tipo de cultura                      ✅ PASS
Cenário: Excluir tipo de cultura e confirmar             ✅ PASS
Cenário: Listar todos os dispositivos IoT                ✅ PASS
Cenário: Buscar dispositivos por talhão                  ✅ PASS
Cenário: Cadastrar novo sensor IoT                       ✅ PASS
Cenário: Atualizar status de dispositivo                 ✅ PASS
Cenário: Listar todos os alertas ativos                  ✅ PASS
Cenário: Criar novo alerta para um talhão                ✅ PASS
Cenário: Resolver alerta (preenche resolvedAt)           ✅ PASS
Cenário: Buscar alertas por talhão específico            ✅ PASS
Cenário: Criar alerta sem tipo deve falhar               ✅ PASS
Cenário: Excluir alerta existente                        ✅ PASS
Cenário: Validar autenticação JWT em endpoints protegidos ✅ PASS
Cenário: Executar requisições `/api/*` com cabeçalho `Authorization: Bearer ...` ✅ PASS
──────────────────────────────────────────────
27 cenários | 27 aprovados | 0 falhas
BUILD SUCCESS
──────────────────────────────────────────────
```

Os relatórios completos do Cucumber estão em:
- `docs/cucumber-report.html` — relatório visual
- `docs/cucumber-report.json` — dados estruturados

---

## 🗄 Banco de Dados

O projeto utiliza **H2 Database** em memória com inicialização automática via `schema.sql` e `data.sql`.

### Modelo de Dados

```
users ──────┐
            ├── farm_users ──── farms ──── fields ──┬── iot_devices ── iot_readings
            │                                       ├── field_crops
crop_types ─┼── decision_rules                      ├── satellite_observations
            └── field_crops                         ├── alerts
                                                    └── automation_actions
```

### Tabelas

| Tabela | Descrição |
|---|---|
| `users` | Usuários da plataforma |
| `farms` | Fazendas/propriedades rurais |
| `farm_users` | Relação N:N entre fazendas e usuários |
| `fields` | Talhões/campos dentro de fazendas |
| `crop_types` | Tipos de cultura com parâmetros ideais |
| `field_crops` | Culturas plantadas nos talhões |
| `decision_rules` | Regras de decisão por tipo de cultura |
| `iot_devices` | Sensores e dispositivos IoT |
| `iot_readings` | Leituras dos dispositivos |
| `satellite_observations` | Dados de satélite (NDVI, temp, umidade) |
| `alerts` | Alertas gerados pelo sistema |
| `automation_actions` | Ações automatizadas (irrigação, etc.) |

### Dados Seed

A aplicação já vem com dados de exemplo:
- 2 fazendas (SP e MG)
- 4 talhões
- 4 tipos de cultura (Soja, Milho, Café, Alface)
- 5 dispositivos IoT
- 3 alertas

---

<p align="center">
  <b>Sylo</b> — Agricultura inteligente para o futuro 🌱
</p>
