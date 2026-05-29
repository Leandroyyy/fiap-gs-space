# language: pt
Funcionalidade: Gerenciamento de Tipos de Cultura
  Como agrônomo
  Eu quero gerenciar os tipos de cultura agrícola
  Para definir parâmetros ideais de cultivo

  Cenário: Listar todos os tipos de cultura
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/crop-types"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 4 fazendas

  Cenário: Buscar tipo de cultura por ID
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/crop-types/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "name" com valor "Soja"

  Cenário: Criar novo tipo de cultura com parâmetros ideais
    Dado que eu tenho os dados de um novo tipo de cultura:
      | name                 | Trigo                              |
      | description          | Triticum aestivum - cereal inverno |
      | idealMinSoilMoisture | 35.00                              |
      | idealMaxSoilMoisture | 65.00                              |
      | idealMinTemperature  | 10.00                              |
      | idealMaxTemperature  | 24.00                              |
      | idealMinNdvi         | 0.45                               |
      | idealMaxNdvi         | 0.80                               |
    Quando eu enviar uma requisição POST para "/api/crop-types"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "name" com valor "Trigo"
    E a resposta deve conter o campo "id"

  Cenário: Excluir tipo de cultura e confirmar remoção
    Dado que eu tenho os dados de um novo tipo de cultura:
      | name                 | Cultura Temporária                 |
      | description          | Cultura para teste de exclusão     |
    Quando eu enviar uma requisição POST para "/api/crop-types"
    Então o status da resposta deve ser 201
    Quando eu enviar uma requisição DELETE para o recurso criado em "/api/crop-types"
    Então o status da resposta deve ser 204
