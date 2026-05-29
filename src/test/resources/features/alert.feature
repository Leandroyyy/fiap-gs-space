# language: pt
Funcionalidade: Gerenciamento de Alertas
  Como operador do sistema Sylo
  Eu quero gerenciar alertas de monitoramento
  Para acompanhar problemas nas áreas de cultivo

  Cenário: Listar todos os alertas ativos
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/alerts/status/ACTIVE"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 2 fazendas

  Cenário: Criar novo alerta para um talhão
    Dado que eu tenho os dados de um novo alerta:
      | fieldId     | 1                                              |
      | fieldCropId | 1                                              |
      | alertType   | PEST_DETECTED                                  |
      | severity    | CRITICAL                                       |
      | message     | Presença de lagarta detectada no Talhão A1      |
      | status      | ACTIVE                                         |
    Quando eu enviar uma requisição POST para "/api/alerts"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "alertType" com valor "PEST_DETECTED"
    E a resposta deve conter o campo "severity" com valor "CRITICAL"
    E a resposta deve conter o campo "status" com valor "ACTIVE"

  Cenário: Resolver alerta existente deve preencher resolvedAt
    Dado que eu tenho os dados de um novo alerta:
      | fieldId     | 1                                              |
      | fieldCropId | 1                                              |
      | alertType   | LOW_MOISTURE                                   |
      | severity    | WARNING                                        |
      | message     | Umidade normalizada após irrigação              |
      | status      | RESOLVED                                       |
    Quando eu enviar uma requisição PUT para "/api/alerts/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "status" com valor "RESOLVED"
    E a resposta deve conter o campo "resolvedAt"

  Cenário: Buscar alertas por talhão específico
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/alerts/field/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas

  Cenário: Criar alerta sem tipo deve falhar com validação
    Dado que eu tenho os dados de um novo alerta:
      | fieldId     | 1                                              |
      | alertType   |                                                |
      | severity    | WARNING                                        |
      | message     | Alerta sem tipo                                 |
      | status      | ACTIVE                                         |
    Quando eu enviar uma requisição POST para "/api/alerts"
    Então o status da resposta deve ser 400

  Cenário: Excluir alerta existente
    Dado que eu tenho os dados de um novo alerta:
      | fieldId     | 1                                              |
      | alertType   | TEMPORARY_ALERT                                |
      | severity    | INFO                                           |
      | message     | Alerta temporário para teste de exclusão        |
      | status      | ACTIVE                                         |
    Quando eu enviar uma requisição POST para "/api/alerts"
    Então o status da resposta deve ser 201
    Quando eu enviar uma requisição DELETE para o recurso criado em "/api/alerts"
    Então o status da resposta deve ser 204
