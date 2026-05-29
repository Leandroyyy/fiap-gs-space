package br.com.sylo.sylo.bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private Map<String, String> requestBody;
    private Long lastCreatedId;

    // ================= DADO =================

    @Dado("que existem fazendas cadastradas no sistema")
    public void queExistemFazendasCadastradasNoSistema() {
        // Os dados seed já são carregados automaticamente pelo schema.sql e data.sql
        ResponseEntity<String> check = restTemplate.getForEntity("/api/farms", String.class);
        assertThat(check.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Dado("que eu tenho os dados de uma nova fazenda:")
    public void queEuTenhoOsDadosDeUmaNovaFazenda(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    @Dado("que eu tenho os dados atualizados da fazenda:")
    public void queEuTenhoOsDadosAtualizadosDaFazenda(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    @Dado("que eu tenho os dados de um novo talhão:")
    public void queEuTenhoOsDadosDeUmNovoTalhao(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    @Dado("que eu tenho os dados de um novo alerta:")
    public void queEuTenhoOsDadosDeUmNovoAlerta(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    @Dado("que eu tenho os dados de um novo dispositivo IoT:")
    public void queEuTenhoOsDadosDeUmNovoDispositivoIoT(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    @Dado("que eu tenho os dados de um novo tipo de cultura:")
    public void queEuTenhoOsDadosDeUmNovoTipoDeCultura(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        dataTable.asMap(String.class, String.class).forEach((key, value) ->
                requestBody.put(key, value)
        );
    }

    // ================= QUANDO =================

    @Quando("eu enviar uma requisição GET para {string}")
    public void euEnviarUmaRequisicaoGETPara(String endpoint) {
        response = restTemplate.getForEntity(endpoint, String.class);
    }

    @Quando("eu enviar uma requisição POST para {string}")
    public void euEnviarUmaRequisicaoPOSTPara(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = buildJsonBody();
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        response = restTemplate.postForEntity(endpoint, entity, String.class);

        // Armazenar o ID do recurso criado se status 201
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            String body = response.getBody();
            if (body.contains("\"id\":")) {
                String idStr = body.split("\"id\":")[1].split("[,}]")[0].trim();
                lastCreatedId = Long.parseLong(idStr);
            }
        }
    }

    @Quando("eu enviar uma requisição PUT para {string}")
    public void euEnviarUmaRequisicaoPUTPara(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = buildJsonBody();
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        response = restTemplate.exchange(endpoint, HttpMethod.PUT, entity, String.class);
    }

    @Quando("eu enviar uma requisição DELETE para {string}")
    public void euEnviarUmaRequisicaoDELETEPara(String endpoint) {
        response = restTemplate.exchange(endpoint, HttpMethod.DELETE, null, String.class);
    }

    @Quando("eu enviar uma requisição DELETE para o recurso criado em {string}")
    public void euEnviarUmaRequisicaoDELETEParaORecursoCriadoEm(String basePath) {
        assertThat(lastCreatedId).isNotNull();
        String endpoint = basePath + "/" + lastCreatedId;
        response = restTemplate.exchange(endpoint, HttpMethod.DELETE, null, String.class);
    }

    // ================= ENTÃO =================

    @Então("o status da resposta deve ser {int}")
    public void oStatusDaRespostaDeveSer(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @E("a resposta deve conter uma lista de fazendas")
    public void aRespostaDeveConterUmaLista() {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).startsWith("[");
    }

    @E("a lista deve conter pelo menos {int} fazendas")
    public void aListaDeveConterPeloMenosNFazendas(int minCount) {
        assertThat(response.getBody()).isNotNull();
        // Contar ocorrências de "id": no array JSON para verificar quantidade
        String body = response.getBody();
        long count = body.chars().filter(ch -> ch == '{').count();
        assertThat(count).isGreaterThanOrEqualTo(minCount);
    }

    @E("a resposta deve conter o campo {string} com valor {string}")
    public void aRespostaDeveConterOCampoComValor(String field, String value) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"" + field + "\"");
        assertThat(response.getBody()).contains(value);
    }

    @E("a resposta deve conter o campo {string}")
    public void aRespostaDeveConterOCampo(String field) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"" + field + "\"");
    }

    // ================= HELPERS =================

    private String buildJsonBody() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : requestBody.entrySet()) {
            if (!first) sb.append(",");
            first = false;

            String key = entry.getKey();
            String value = entry.getValue();

            sb.append("\"").append(key).append("\":");

            if (value == null || value.isBlank()) {
                // Para campos obrigatórios vazios, enviar string vazia
                sb.append("\"\"");
            } else if (isNumeric(value)) {
                sb.append(value);
            } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                sb.append(value.toLowerCase());
            } else if (value.equalsIgnoreCase("null")) {
                sb.append("null");
            } else {
                sb.append("\"").append(value).append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isBlank()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
