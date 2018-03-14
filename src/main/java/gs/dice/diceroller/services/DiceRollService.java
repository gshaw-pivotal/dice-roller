package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.models.DieRollStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
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
            rollResults.add(roll(1, dieRoll.getDieType()));
        }

        dieRoll.setRollResults(rollResults);

        gatherRollStats(dieRoll);

        return dieRoll;
    }

    private int roll(int minValue, int maxValue) {
        SecureRandom random = new SecureRandom();
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }

    private void gatherRollStats(DieRoll dieRoll) {

        DieRollStats rollStats = DieRollStats.builder()
                .mean(diceRollStats.determineRollsMean(dieRoll.getRollResults()))
                .sum(diceRollStats.determineRollsSum(dieRoll.getRollResults()))
                .median(diceRollStats.determineRollsMedian(dieRoll.getRollResults()))
                .max(diceRollStats.maxRoll(dieRoll.getRollResults()))
                .min(diceRollStats.minRoll(dieRoll.getRollResults()))
                .rollValueOccurrence(diceRollStats.rollValueOccurrence(dieRoll.getRollResults(), dieRoll.getDieType()))
                .build();

        dieRoll.setDieRollStats(rollStats);
    }
}
