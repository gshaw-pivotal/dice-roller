package gs.dice.diceroller.controllers;

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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DiceRollController {

    @Autowired
    private DiceRollService diceRollService;

    @RequestMapping(value = "/roll", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DieRoll> rollDiceRequest(@Valid @RequestBody JsonNode request) {
        try {
            DieRoll processedDieRoll = diceRollService.generateRolls(convertJsonToPojo(request));
            return new ResponseEntity(processedDieRoll, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(
                    "Invalid request body for a single die type roll request",
                    HttpStatus.BAD_REQUEST);
        }
    }

    private DieRoll convertJsonToPojo(JsonNode jsonNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DieRoll dieRoll = mapper.readValue(jsonNode.toString(), DieRoll.class);
        return dieRoll;
    }
}
