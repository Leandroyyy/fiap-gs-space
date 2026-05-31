# language: pt
Funcionalidade: Autenticação e Segurança
  Como usuário da API Sylo
  Eu quero autenticar e testar o acesso a recursos protegidos
  Para garantir que apenas usuários válidos consigam consumir a API

  Cenário: Registrar novo usuário e obter token JWT
    Dado que eu tenho os dados de registro de usuário:
      | name     | Usuário BDD                        |
      | email    | bdd-security-{nanoTime}@sylo.com.br |
      | password | Sylo@2026                          |
    Quando eu enviar uma requisição POST para "/auth/register"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "token"

  Cenário: Acessar recurso protegido sem autenticação deve falhar
    Dado que eu não estou autenticado
    Quando eu enviar uma requisição GET para "/api/farms"
    Então o status da resposta deve ser 401