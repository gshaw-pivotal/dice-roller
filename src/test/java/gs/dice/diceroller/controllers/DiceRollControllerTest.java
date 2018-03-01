package gs.dice.diceroller.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void post_receivesAValidRequestToRollASingleTypeOfDie_returnsResponseWithTheDieType() throws Exception {
        int dieType = 6;
        String expectedResponse = "\"dieType\": " + dieType;

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieRollRequestBody(dieType)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString(), containsString(expectedResponse));
    }

    @Test
    public void post_receivesAValidRequestForMultipleRollsOfASingleDieType_returnsRollResultsAsAList() throws Exception {
        String rollResultsPattern = ".*\"results\":\\s\\[[\\d,\\s]*].*";

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidMultiDieRollRequestBody(6, 2)))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().matches(rollResultsPattern));
    }

    @Test
    public void post_receivesAValidRequestForMultipleRollsOfASingleDieType_returnsCorrectNumberOfRollResults() throws Exception {
        int numberOfRolls = 2;

        String rollResultsSegmentPattern = "\"results\":\\s\\[[\\d,\\s]*]";
        String rollResultsPattern = "\\[.*\\]";

        Pattern resultSegmentPattern = Pattern.compile(rollResultsSegmentPattern);
        Pattern resultRollPattern = Pattern.compile(rollResultsPattern);

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidMultiDieRollRequestBody(6, numberOfRolls)))
                .andExpect(status().isOk())
                .andReturn();

        Matcher resultSegmentMatcher = resultSegmentPattern.matcher(result.getResponse().getContentAsString());
        resultSegmentMatcher.find();

        Matcher rollResultsMatcher = resultRollPattern.matcher(resultSegmentMatcher.group(0));
        rollResultsMatcher.find();
        String rollResults = rollResultsMatcher.group(0).replaceAll("\\[", "").replaceAll("\\]", "");

        assertThat(rollResults.split(",").length, equalTo(numberOfRolls));
    }

    private String buildValidSingleDieRollRequestBody(int dieType) {
        return "{" +
                "\"dieType\": " + dieType + "," +
                "\"rollCount\": 1" +
                "}";
    }

    private String buildValidMultiDieRollRequestBody(int dieType, int rollCount) {
        return "{" +
                "\"dieType\": " + dieType + "," +
                "\"rollCount\": " + rollCount +
                "}";
    }

    private String buildInvalidSingleDieRollRequestBody() {
        return "{" +
                "\"rollCount\": 1" +
                "}";
    }
}