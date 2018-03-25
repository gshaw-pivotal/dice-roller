package gs.dice.diceroller.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.services.DiceRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DiceRollController {

    @Autowired
    private DiceRollService diceRollService;

    @RequestMapping(value = "/roll",
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> rollDiceRequest(@Valid @RequestBody JsonNode request) {
        if (request.has("dies") && request.get("dies").isArray()) {
            try {
                List<DieRoll> processedDieRolls = diceRollService.generateRolls(convertJsonToPojoList(request));
                return buildResponse(
                        "{\"dies\": " + convertListToJson(processedDieRolls) + "}",
                        HttpStatus.OK);
            }
            catch (IOException e) {
                return buildResponse(
                        "Invalid request body for a multiple die type roll request",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                DieRoll processedDieRoll = diceRollService.generateRolls(convertJsonToPojo(request));
                return buildResponse(processedDieRoll, HttpStatus.OK);
            } catch (IOException e) {
                return buildResponse("Invalid request body for a single die type roll request",
                        HttpStatus.BAD_REQUEST);
            }
        }
    }

    private ResponseEntity<Object> buildResponse(Object body, HttpStatus status) {
        return new ResponseEntity(body, status);
    }

    private List<DieRoll> convertJsonToPojoList(JsonNode jsonNode) throws IOException {
        List<DieRoll> rollRequests = new ArrayList();

        JsonNode list = jsonNode.get("dies");
        for (int count = 0; count < list.size(); count++) {
            rollRequests.add(convertJsonToPojo(list.get(count)));
        }

        return rollRequests;
    }

    private DieRoll convertJsonToPojo(JsonNode jsonNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DieRoll dieRoll = mapper.readValue(jsonNode.toString(), DieRoll.class);
        return dieRoll;
    }

    private String convertListToJson(List<DieRoll> dieRolls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dieRolls);
    }
}
