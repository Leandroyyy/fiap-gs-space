# language: pt
Funcionalidade: Gerenciamento de Fazendas
  Como administrador da plataforma Sylo
  Eu quero gerenciar fazendas
  Para organizar as propriedades rurais no sistema

  Cenário: Listar todas as fazendas cadastradas
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/farms"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 2 fazendas

  Cenário: Buscar fazenda por ID existente
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/farms/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "name" com valor "Fazenda Boa Vista"
    E a resposta deve conter o campo "city" com valor "Ribeirão Preto"

  Cenário: Criar nova fazenda com dados válidos
    Dado que eu tenho os dados de uma nova fazenda:
      | name        | Fazenda Primavera                  |
      | description | Fazenda de hortaliças orgânicas    |
      | city        | Campinas                           |
      | state       | SP                                 |
      | latitude    | -22.9099                           |
      | longitude   | -47.0626                           |
    Quando eu enviar uma requisição POST para "/api/farms"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "name" com valor "Fazenda Primavera"
    E a resposta deve conter o campo "id"

  Cenário: Criar fazenda sem nome deve falhar com validação
    Dado que eu tenho os dados de uma nova fazenda:
      | name        |                                     |
      | description | Fazenda sem nome                    |
      | city        | São Paulo                           |
      | state       | SP                                  |
    Quando eu enviar uma requisição POST para "/api/farms"
    Então o status da resposta deve ser 400

  Cenário: Atualizar fazenda existente
    Dado que existem fazendas cadastradas no sistema
    E que eu tenho os dados atualizados da fazenda:
      | name        | Fazenda Boa Vista (Atualizada)      |
      | description | Fazenda de soja, milho e sorgo      |
      | city        | Ribeirão Preto                      |
      | state       | SP                                  |
      | latitude    | -21.1767                            |
      | longitude   | -47.8208                            |
    Quando eu enviar uma requisição PUT para "/api/farms/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "name" com valor "Fazenda Boa Vista (Atualizada)"

  Cenário: Buscar fazenda com ID inexistente
    Quando eu enviar uma requisição GET para "/api/farms/999"
    Então o status da resposta deve ser 404
