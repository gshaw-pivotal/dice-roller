package gs.dice.diceroller.services;

import java.util.Collections;
import java.util.List;

public class DiceRollStats {
    public float determineRollsMean(List<Integer> rolls) {

        int numberOfRolls = rolls.size();

        int rollSummation = rolls.stream().mapToInt(roll -> roll).sum();

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
}
