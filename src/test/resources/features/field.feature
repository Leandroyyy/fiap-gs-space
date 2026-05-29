# language: pt
Funcionalidade: Gerenciamento de Talhões
  Como gestor agrícola
  Eu quero gerenciar talhões dentro das fazendas
  Para organizar as áreas de cultivo

  Cenário: Listar todos os talhões
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/fields"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 4 fazendas

  Cenário: Buscar talhões de uma fazenda específica
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/fields/farm/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 2 fazendas

  Cenário: Criar novo talhão vinculado a uma fazenda
    Dado que eu tenho os dados de um novo talhão:
      | farmId       | 1              |
      | name         | Talhão C1      |
      | areaHectares | 30.00          |
      | latitude     | -21.2000       |
      | longitude    | -47.8500       |
      | status       | ACTIVE         |
    Quando eu enviar uma requisição POST para "/api/fields"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "name" com valor "Talhão C1"
    E a resposta deve conter o campo "farmName" com valor "Fazenda Boa Vista"

  Cenário: Criar talhão sem nome deve falhar
    Dado que eu tenho os dados de um novo talhão:
      | farmId       | 1              |
      | name         |                |
      | areaHectares | 10.00          |
    Quando eu enviar uma requisição POST para "/api/fields"
    Então o status da resposta deve ser 400

  Cenário: Criar talhão com fazenda inexistente deve falhar
    Dado que eu tenho os dados de um novo talhão:
      | farmId       | 999            |
      | name         | Talhão Fantasma|
      | areaHectares | 10.00          |
      | status       | ACTIVE         |
    Quando eu enviar uma requisição POST para "/api/fields"
    Então o status da resposta deve ser 404
