package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.models.DieRollStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiceRollService {

    @Autowired
    private DiceRollStats diceRollStats;

    public DieRoll generateRolls(DieRoll dieRoll) {
        int numberOfRolls = dieRoll.getRollCount();

        List<Integer> rollResults = new ArrayList();

        for (int count = 0; count < numberOfRolls; count++) {
            rollResults.add(roll());
        }

        dieRoll.setRollResults(rollResults);

        gatherRollStats(dieRoll);

        return dieRoll;
    }

    private int roll() {
        return 1;
    }

    private void gatherRollStats(DieRoll dieRoll) {

        DieRollStats rollStats = DieRollStats.builder()
                .mean(diceRollStats.determineRollsMean(dieRoll.getRollResults()))
                .sum(diceRollStats.determineRollsSum(dieRoll.getRollResults()))
                .median(diceRollStats.determineRollsMedian(dieRoll.getRollResults()))
                .max(diceRollStats.maxRoll(dieRoll.getRollResults()))
                .min(diceRollStats.minRoll(dieRoll.getRollResults()))
                .build();

        dieRoll.setDieRollStats(rollStats);
    }
}
