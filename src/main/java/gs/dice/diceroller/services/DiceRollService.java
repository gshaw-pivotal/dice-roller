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
    private DiceRollStatCalculator diceRollStatCalculator;

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

    public List<DieRoll> generateRolls(List<DieRoll> dieRollRequestList) {
        for (int count = 0; count < dieRollRequestList.size(); count++) {
            dieRollRequestList.set(count, generateRolls(dieRollRequestList.get(count)));
        }

        return dieRollRequestList;
    }

    private int roll(int minValue, int maxValue) {
        SecureRandom random = new SecureRandom();
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }

    private void gatherRollStats(DieRoll dieRoll) {

        DieRollStats rollStats = DieRollStats.builder()
                .mean(diceRollStatCalculator.determineRollsMean(dieRoll.getRollResults()))
                .sum(diceRollStatCalculator.determineRollsSum(dieRoll.getRollResults()))
                .median(diceRollStatCalculator.determineRollsMedian(dieRoll.getRollResults()))
                .max(diceRollStatCalculator.maxRoll(dieRoll.getRollResults()))
                .min(diceRollStatCalculator.minRoll(dieRoll.getRollResults()))
                .rollValueOccurrence(diceRollStatCalculator.rollValueOccurrence(dieRoll.getRollResults(), dieRoll.getDieType()))
                .build();

        dieRoll.setDieRollStats(rollStats);
    }
}
