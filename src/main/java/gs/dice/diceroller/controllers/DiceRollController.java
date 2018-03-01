package gs.dice.diceroller.controllers;

import gs.dice.diceroller.models.DieRoll;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DiceRollController {

    @RequestMapping(value = "/roll", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public String rollDiceRequest(@Valid @RequestBody DieRoll requestedDieRoll) {
        return buildResponse(requestedDieRoll);
    }

    private String buildResponse(DieRoll requestedDieRoll) {
        return "{" +
                "\"dieType\": " + requestedDieRoll.getDieType() + "," +
                "\"results\": " + generateRolls(requestedDieRoll.getRollCount()) +
                "}";
    }

    private String generateRolls(int numberOfRolls) {
        String results = "[";

        for (int count = 0; count < numberOfRolls - 1; count++) {
            results = results + (count+1) + ",";
        }

        results = results + numberOfRolls;
        results = results + "]";

        return results;
    }
}
