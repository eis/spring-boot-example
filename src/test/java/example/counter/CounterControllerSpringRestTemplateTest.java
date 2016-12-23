package example.counter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.net.HttpHeaders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CounterControllerSpringRestTemplateTest {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        base = new URL("http://localhost:" + port + "/add");
        template = new TestRestTemplate();
    }

    @Test
    public void testAdd() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        CounterRequest cRequest = new CounterRequest(2, 3);
        
        HttpEntity<CounterRequest> request = new HttpEntity<CounterRequest>(
                cRequest, headers);

        ResponseEntity<CounterResult> response = template.postForEntity(base.toString(), request,
                CounterResult.class);
        assertThat(response.getBody(), equalTo(new CounterResult(5)));
    }
}