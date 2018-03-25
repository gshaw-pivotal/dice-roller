package gs.dice.diceroller.services;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DiceRollStatCalculator {
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
        List<Integer> sortedRolls = new ArrayList(rolls);
        Collections.sort(sortedRolls);
        if (sortedRolls.size() % 2  == 0) {
            int lowIndex = sortedRolls.size() / 2;
            return (float)(sortedRolls.get(lowIndex) + sortedRolls.get(lowIndex - 1)) / (float)2;
        } else {
            int medianIndex = sortedRolls.size() / 2;
            return sortedRolls.get(medianIndex);
        }
    }

    public Map<Integer, Integer> rollValueOccurrence(List<Integer> rolls, int dieType) {
        Map<Integer, Integer> rollValueSummation = new HashMap();

        for (int count = 1; count <= dieType; count++) {
            rollValueSummation.put(count, 0);
        }

        rolls.forEach(roll -> rollValueSummation.put(roll, (rollValueSummation.get(roll) + 1)));

        return rollValueSummation;
    }
}
