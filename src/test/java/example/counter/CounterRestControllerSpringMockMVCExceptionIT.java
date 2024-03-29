package example.counter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CounterRestController.class)
public class CounterRestControllerSpringMockMVCExceptionIT {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CounterService counterService;
    
    @Test
    public void unauthorizedExceptionFlowsThrough() throws Exception {
        given(this.counterService.count(notNull()))
            .willThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        this.mvc.perform(post(CounterRestController.ADD_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"int1\":1, \"int2\":2}")
                )
             .andExpect(status().isUnauthorized());
    }
}
