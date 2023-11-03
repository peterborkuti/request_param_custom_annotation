package hu.bp.rqheader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RqControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getShouldReturnWithEmailHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-EMAIL", "EMAIL");
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/", HttpMethod.GET, entity, String.class);
        assertEquals("EMAIL", response.getBody());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getShouldReturnWithBadRequestWhenHeaderIsNotExistsOrNullOrEmpty(int headerIndex) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        setHeader(headers, headerIndex);
        HttpEntity<String> entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private void setHeader(HttpHeaders headers, int headerIndex) {
        if (headerIndex == 1) {
            headers.set("X-EMAIL", null);
        }

        if (headerIndex == 2) {
            headers.set("X-EMAIL", "");
        }
    }

}