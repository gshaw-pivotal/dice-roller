package gs.dice.diceroller.controllers;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.services.DiceRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DiceRollController {

    @Autowired
    private DiceRollService diceRollService;

    @RequestMapping(value = "/roll", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public String rollDiceRequest(@Valid @RequestBody DieRoll requestedDieRoll) {
        DieRoll processedDieRoll = diceRollService.generateRolls(requestedDieRoll);
        return buildResponse(processedDieRoll);
    }

    private String buildResponse(DieRoll requestedDieRoll) {
        return "{" +
                "\"dieType\": " + requestedDieRoll.getDieType() + "," +
                "\"results\": " + generateResultList(requestedDieRoll) +
                "}";
    }

    private String generateResultList(DieRoll dieRoll) {
        int numberOfRolls = dieRoll.getRollCount();
        List<Integer> rollResults = dieRoll.getRollResults();
        String results = "[";

        for (int count = 0; count < numberOfRolls - 1; count++) {
            results = results + rollResults.get(count) + ",";
        }

        results = results + rollResults.get(rollResults.size() - 1);
        results = results + "]";

        return results;
    }
}
