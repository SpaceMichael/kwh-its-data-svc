package hk.org.ha.kcc.its.contoller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint;
import hk.org.ha.kcc.common.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EquipUsageRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //@Value("${app.auth.url}")
    private String jwtTokenUrl ="https://sam3-auth-app-appsvc-corp-sam-sit-1.tstcld61.server.ha.org.hk/auth/realms/kwh/protocol/openid-connect/token";

    private static final RestTemplate restTemplate = new RestTemplate();

    // test create EquipUsageRequest
   /* @Test
    public void createEquipUsageRequest() throws Exception {
        // Step 1: Get the JWT token
        String jwtToken = getJwtToken();
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Step 2: Use the obtained JWT token in another API request
        String url = "/api/v1/equip-usage/requests";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);

        // Step 3 create new equipUsageRequest
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .headers(headers)
                //.contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eamNo\": 1824496,\n" +
                        "    \"serNo\": \"E09-123567890123\",\n" +
                        "    \"model\": \"ELITDESK PU 8000G5 SFF\",\n" +
                        "    \"belongTo\": \"KWH Ward 8A\",\n" +
                        "    \"type\": \"MED\",\n" +
                        "    \"caseNo\": \"HN123454677\",\n" +
                        "    \"patientName\": \"KC\",\n" +
                        "    \"activeFlag\": true,\n" +
                        "    \"date\": \"2021-08-01\",\n" +
                        "    \"time\": \"10:00\"\n" +
                        "}");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andReturn();

    }


    private String getJwtToken() {
        String jwtToken = null;
        String url = jwtTokenUrl;
        WebClient webClient = WebClient.create(jwtTokenUrl);
        try {
            // use MultiValueMap to store the request body
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", "kwh-its-data-svc");
            requestBody.add("username", "ttk799");
            requestBody.add("password", "Spacemic3434");
            requestBody.add("grant_type", "password");

            *//*Map<String, String> requestBody = new HashMap<>();
            requestBody.put("client_id", "kwh-its-data-svc");
            requestBody.put("username", "ttk799");
            requestBody.put("password", "Spacemic3434");
            requestBody.put("grant_type", "password");*//*


            // use webcleint to call token api and get the responce
            Jwt jwt = webClient.post()
                    .uri(url)
                    .body(BodyInserters.fromFormData(requestBody))
                    .retrieve()
                    .bodyToMono(Jwt.class)
                    .block();
            //Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(jwt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwtToken;
    }*/
}
