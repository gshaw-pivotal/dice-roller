package gs.dice.diceroller.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class DiceRollStatsTest {

    private DiceRollStats diceRollStats;

    @Before
    public void setup() {
        diceRollStats = new DiceRollStats();
    }

    @Test
    public void givenAListOfRollResultsThatContainsASingleRoll_thenTheMeanIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);

        float meanOfRolls = diceRollStats.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(5F));
    }

    @Test
    public void givenAListOfTwoRollResults_thenTheMeanIsReportedTheAverageOfTheTwoRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(3);

        float meanOfRolls = diceRollStats.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(2F));
    }
}