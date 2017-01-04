package example.counter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@WebMvcTest(CounterController.class)
public class CounterControllerSpringMockMVCExceptionIT {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CounterService counterService;
    
    @Test
    public void getVehicleShouldReturnMakeAndModel() throws Exception {
        given(this.counterService.count(notNull(CounterRequest.class)))
            .willThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        this.mvc.perform(post("/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"int1\":1, \"int2\":2}")
                )
             .andExpect(status().isUnauthorized());
    }
}
