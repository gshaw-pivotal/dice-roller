package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;

import java.util.ArrayList;
import java.util.List;

public class DiceRollService {

    public DieRoll roll(DieRoll dieRoll) {
        int numberOfRolls = dieRoll.getRollCount();

        List<Integer> rollResults = new ArrayList();

        for (int count = 0; count < numberOfRolls; count++) {
            rollResults.add(roll());
        }

        dieRoll.setRollResults(rollResults);
        return dieRoll;
    }

    private int roll() {
        return 1;
    }
}
