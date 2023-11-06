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

    public static final String EMAIL_HEADER = "X-EMAIL";
    public static final String TOKEN_HEADER = "X-EMAIL-TOKEN";

    public static final String EMAIL_VALUE = "a@b.c";
    public static final String TOKEN_VALUE = "123456789";

    private static final String HOST = "localhost";
    private static final String PROTOCOL = "http://";

    @Test
    void getEmailShouldReturnWithEmailHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(EMAIL_HEADER, EMAIL_VALUE);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/email", HttpMethod.GET, entity, String.class);
        assertEquals(EMAIL_VALUE, response.getBody());
    }

    @Test
    void getTokenShouldReturnWithEmailTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(TOKEN_HEADER, TOKEN_VALUE);
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/token", HttpMethod.GET, entity, String.class);
        assertEquals(TOKEN_VALUE, response.getBody());
    }

    @Test
    void getEmailAndTokenShouldReturnWithEmailAndEmailTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(EMAIL_HEADER, EMAIL_VALUE);
        headers.add(TOKEN_HEADER, TOKEN_VALUE);
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/email-and-token", HttpMethod.GET, entity, String.class);
        assertEquals(EMAIL_VALUE+TOKEN_VALUE, response.getBody());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getEmailShouldReturnWithBadRequestWhenHeaderIsNotExistsOrNullOrEmpty(int headerIndex) {
        HttpHeaders headers = new HttpHeaders();
        setHeaders(headers, headerIndex, EMAIL_HEADER);
        HttpEntity<String> entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/email", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getTokenShouldReturnWithBadRequestWhenHeaderIsNotExistsOrNullOrEmpty(int headerIndex) {
        HttpHeaders headers = new HttpHeaders();
        setHeaders(headers, headerIndex, TOKEN_HEADER);
        HttpEntity<String> entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(getBaseUrl() + "/token", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private void setHeaders(HttpHeaders headers, int headerIndex, String header) {
        if (headerIndex == 1) {
            headers.add(header, null);
        }

        if (headerIndex == 2) {
            headers.add(header, "");
        }
    }

    private String getBaseUrl(){
        return PROTOCOL + HOST + ":" + port;
    }

}