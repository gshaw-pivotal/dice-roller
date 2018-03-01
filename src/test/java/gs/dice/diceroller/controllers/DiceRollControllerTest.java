package gs.dice.diceroller.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DiceRollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private DiceRollController diceRollController;

    @Before
    public void setup() {
        diceRollController = new DiceRollController();
    }

    @Test
    public void post_receivesAValidCallToRollASingle6SidedDie_returnsSingleResult() throws Exception {
        mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieRollRequestBody(6)))
                .andExpect(status().isOk());
    }

    @Test
    public void post_receivesAnInvalidCallToRollASingleDie_returnsBadRequest() throws Exception {
        mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildInvalidSingleDieRollRequestBody()))
                .andExpect(status().isBadRequest());
    }

    private String buildValidSingleDieRollRequestBody(int dieType) {
        return "{" +
                "\"dieType\": " + dieType + "," +
                "\"rollCount\": 1" +
                "}";
    }

    private String buildInvalidSingleDieRollRequestBody() {
        return "{" +
                "\"rollCount\": 1" +
                "}";
    }
}