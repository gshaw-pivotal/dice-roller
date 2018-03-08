package gs.dice.diceroller.services;

import java.util.Collections;
import java.util.List;

public class DiceRollStats {
    public float determineRollsMean(List<Integer> rolls) {

        int numberOfRolls = rolls.size();

        int rollSummation = determineRollsSum(rolls);

        return (float)rollSummation / (float)numberOfRolls;
    }

    public int determineRollsSum(List<Integer> rolls) {
        return rolls.stream().mapToInt(roll -> roll).sum();
    }

    public int maxRoll(List<Integer> rolls) {
        return Collections.max(rolls);
    }

    public int minRoll(List<Integer> rolls) {
        return Collections.min(rolls);
    }

    public float determineRollsMedian(List<Integer> rolls) {
        Collections.sort(rolls);
        if (rolls.size() % 2  == 0) {
            int lowIndex = rolls.size() / 2;
            return (float)(rolls.get(lowIndex) + rolls.get(lowIndex - 1)) / (float)2;
        } else {
            int medianIndex = rolls.size() / 2;
            return rolls.get(medianIndex);
        }
    }
}
