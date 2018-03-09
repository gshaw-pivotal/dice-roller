package gs.dice.diceroller.controllers;

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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DiceRollController {

    @Autowired
    private DiceRollService diceRollService;

    @RequestMapping(value = "/roll", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DieRoll> rollDiceRequest(@Valid @RequestBody DieRoll requestedDieRoll) {
        DieRoll processedDieRoll = diceRollService.generateRolls(requestedDieRoll);
        return new ResponseEntity(processedDieRoll, HttpStatus.OK);
    }
}
