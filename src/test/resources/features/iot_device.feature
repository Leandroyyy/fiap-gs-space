# language: pt
Funcionalidade: Gerenciamento de Dispositivos IoT
  Como técnico de campo
  Eu quero gerenciar dispositivos IoT
  Para monitorar as condições dos talhões

  Cenário: Listar todos os dispositivos IoT
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/iot-devices"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista de fazendas
    E a lista deve conter pelo menos 5 fazendas

  Cenário: Buscar dispositivos por talhão
    Dado que existem fazendas cadastradas no sistema
    Quando eu enviar uma requisição GET para "/api/iot-devices/field/1"
    Então o status da resposta deve ser 200
    E a lista deve conter pelo menos 2 fazendas

  Cenário: Cadastrar novo sensor IoT
    Dado que eu tenho os dados de um novo dispositivo IoT:
      | fieldId      | 2                        |
      | name         | Sensor Pluviômetro A2    |
      | deviceType   | RAIN_GAUGE               |
      | serialNumber | RG-001-A2                |
      | status       | ONLINE                   |
      | latitude     | -21.1860                 |
      | longitude    | -47.8255                 |
    Quando eu enviar uma requisição POST para "/api/iot-devices"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "name" com valor "Sensor Pluviômetro A2"
    E a resposta deve conter o campo "deviceType" com valor "RAIN_GAUGE"
    E a resposta deve conter o campo "status" com valor "ONLINE"

  Cenário: Atualizar status de dispositivo para OFFLINE
    Dado que eu tenho os dados de um novo dispositivo IoT:
      | fieldId      | 1                                     |
      | name         | Sensor Umidade Solo A1-1              |
      | deviceType   | SOIL_MOISTURE                         |
      | serialNumber | SM-001-A1                             |
      | status       | MAINTENANCE                           |
      | latitude     | -21.1802                              |
      | longitude    | -47.8305                              |
    Quando eu enviar uma requisição PUT para "/api/iot-devices/1"
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "status" com valor "MAINTENANCE"
