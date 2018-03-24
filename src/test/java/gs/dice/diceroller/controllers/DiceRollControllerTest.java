package gs.dice.diceroller.controllers;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.models.DieRollStats;
import gs.dice.diceroller.services.DiceRollService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiceRollControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DiceRollService diceRollService;

    @InjectMocks
    private DiceRollController diceRollController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(diceRollController).build();
    }

    @Test
    public void post_receivesAValidCallToRollASingleDie_returnsSuccessfulResponseStatus() throws Exception {
        int dieType = 6;
        int rollCount = 1;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(buildDieRollObject(dieType, rollCount));

        mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeSingleRollRequestBody(dieType)))
                .andExpect(status().isOk());
    }

    @Test
    public void post_receivesAnInvalidCallToRollASingleDie_returnsBadRequest() throws Exception {
        mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildInvalidSingleRollRequestBody()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_receivesAValidRequestToRollASingleTypeOfDie_returnsResponseWithTheDieType() throws Exception {
        int dieType = 6;
        int rollCount = 1;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(buildDieRollObject(dieType, rollCount));

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeSingleRollRequestBody(dieType)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());

        assertThat(jsonResponse.getInt("dieType"), equalTo(dieType));
    }

    @Test
    public void post_receivesAValidRequestForMultipleRollsOfASingleDieType_returnsRollResultsAsAList() throws Exception {
        int dieType = 6;
        int rollCount = 2;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(buildDieRollObject(dieType, rollCount));

        String rollResultsPattern = "\\[[\\d,\\s]*]";

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeMultiRollRequestBody(dieType, rollCount)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());

        assertTrue(jsonResponse.get("rollResults").toString().matches(rollResultsPattern));
    }

    @Test
    public void post_receivesAValidRequestForMultipleRollsOfASingleDieType_returnsCorrectNumberOfRollResults() throws Exception {
        int dieType = 6;
        int rollCount = 2;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(buildDieRollObject(dieType, rollCount));

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeMultiRollRequestBody(dieType, rollCount)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());
        assertThat(jsonResponse.getJSONArray("rollResults").length(), equalTo(rollCount));
    }

    @Test
    public void post_receivesAValidRequestForASingleDieType_returnsAResponseThatContainsTheStats() throws Exception {
        int dieType = 6;
        int rollCount = 2;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(setRollStat(buildDieRollObject(dieType, rollCount)));

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeMultiRollRequestBody(dieType, rollCount)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());
        assertThat(jsonResponse.getJSONObject("dieRollStats"), is(notNullValue()));
    }

    @Test
    public void post_receivesAValidaRequestForASingleRollOfMultipleDieTypes_returnsSuccessfulResponseStatus() throws Exception {
        List<Integer> dieTypesRequest = Arrays.asList(4, 5, 6);

        List<DieRoll> dieRolls = Arrays.asList(
                setRollStat(buildDieRollObject(4, 1)),
                setRollStat(buildDieRollObject(5, 1)),
                setRollStat(buildDieRollObject(6, 1)));

        when(diceRollService.generateRolls(anyList()))
                .thenReturn(dieRolls);

        String request = buildValidMultiDieTypeSingleRollRequestBody(dieTypesRequest);

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void post_receivesAValidaRequestForASingleRollOfMultipleDieTypes_returnsAListOfDiesOfTheCorrectSize() throws Exception {
        List<Integer> dieTypesRequest = Arrays.asList(4, 5, 6);

        List<DieRoll> dieRolls = Arrays.asList(
                setRollStat(buildDieRollObject(4, 1)),
                setRollStat(buildDieRollObject(5, 1)),
                setRollStat(buildDieRollObject(6, 1)));

        when(diceRollService.generateRolls(anyList()))
                .thenReturn(dieRolls);

        String request = buildValidMultiDieTypeSingleRollRequestBody(dieTypesRequest);

        MvcResult result = mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());
        assertThat(jsonResponse.getJSONArray("dies").length(), equalTo(dieTypesRequest.size()));
    }

    @Test
    public void controllerCallsTheServiceToPerformDiceRolls() throws Exception {
        int dieType = 6;
        int rollCount = 1;

        when(diceRollService.generateRolls(any(DieRoll.class)))
                .thenReturn(buildDieRollObject(dieType, rollCount));

        mockMvc.perform(
                post("/roll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildValidSingleDieTypeSingleRollRequestBody(dieType)))
                .andExpect(status().isOk());

        verify(diceRollService).generateRolls(any(DieRoll.class));
    }

    private String buildValidSingleDieTypeSingleRollRequestBody(int dieType) {
        return "{" +
                "\"dieType\": " + dieType + "," +
                "\"rollCount\": 1" +
                "}";
    }

    private String buildValidMultiDieTypeSingleRollRequestBody(List<Integer> dieTypes) {
        List<String> dieRequests = new ArrayList();
        dieTypes.forEach(die -> dieRequests.add(buildValidSingleDieTypeSingleRollRequestBody(die)));

        String formattedRequests = "";
        for (int count = 0; count < dieRequests.size() - 1; count++) {
            formattedRequests += formattedRequests + dieRequests.get(count) + ",";
        }
        formattedRequests += formattedRequests + dieRequests.get(dieRequests.size() - 1);

        String listOfRollRequests = "\"dies\": [" +
                formattedRequests +
                "]";

        return "{" +
                listOfRollRequests +
                "}";
    }

    private String buildValidSingleDieTypeMultiRollRequestBody(int dieType, int rollCount) {
        return "{" +
                "\"dieType\": " + dieType + "," +
                "\"rollCount\": " + rollCount +
                "}";
    }

    private String buildInvalidSingleRollRequestBody() {
        return "{" +
                "\"rollCount\": 1" +
                "}";
    }

    private DieRoll buildDieRollObject(int dieType, int rollCount) {
        List<Integer> rolls = new ArrayList();

        for (int count = 0; count < rollCount; count++) {
            rolls.add(1);
        }

        return DieRoll.builder()
                .dieType(dieType)
                .rollCount(rollCount)
                .rollResults(rolls)
                .build();
    }

    private DieRoll setRollStat(DieRoll dieRoll) {
        Map<Integer, Integer> rollValueOccurrence = new HashMap();
        rollValueOccurrence.put(1, 1);
        rollValueOccurrence.put(1, 2);

        DieRollStats stats = DieRollStats.builder()
                .mean(1F)
                .sum(2)
                .median(1.5F)
                .max(3)
                .min(1)
                .rollValueOccurrence(rollValueOccurrence)
                .build();

        dieRoll.setDieRollStats(stats);
        return dieRoll;
    }
}