package gs.dice.diceroller.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

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
    public void givenAListThatContainsAnOrderedEvenNumberOfRollResults_thenTheMeanIsReportedTheAverageOfTheRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(3);

        float meanOfRolls = diceRollStats.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(2F));
    }

    @Test
    public void givenAListThatContainsAnUnorderedEvenNumberOfRollResults_thenTheMeanIsReportedTheAverageOfTheRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);
        rolls.add(1);
        rolls.add(5);
        rolls.add(6);

        float meanOfRolls = diceRollStats.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(4F));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheSumIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);

        int sumOfRolls = diceRollStats.determineRollsSum(rolls);

        assertThat(sumOfRolls, equalTo(4));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheSumOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(2);
        rolls.add(3);

        int sumOfRolls = diceRollStats.determineRollsSum(rolls);

        assertThat(sumOfRolls, equalTo(5));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMaxIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(6);

        int maxRoll = diceRollStats.maxRoll(rolls);

        assertThat(maxRoll, equalTo(6));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheMaxOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);
        rolls.add(3);

        int maxRoll = diceRollStats.maxRoll(rolls);

        assertThat(maxRoll, equalTo(5));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMinIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(2);

        int minRoll = diceRollStats.minRoll(rolls);

        assertThat(minRoll, equalTo(2));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheMinOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);
        rolls.add(2);

        int minRoll = diceRollStats.minRoll(rolls);

        assertThat(minRoll, equalTo(2));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMedianIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);

        float minRoll = diceRollStats.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(3F));
    }

    @Test
    public void givenAListThatContainsAnOrderedOddNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheMiddleRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);
        rolls.add(5);
        rolls.add(6);

        float minRoll = diceRollStats.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(5F));
    }

    @Test
    public void givenAListThatContainsAnOrderedEvenNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheSumOfTheTwoMiddleRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);
        rolls.add(4);
        rolls.add(5);
        rolls.add(6);

        float minRoll = diceRollStats.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(4.5F));
    }

    @Test
    public void givenAListThatContainsAnUnOrderedOddNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheMiddleRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);
        rolls.add(6);
        rolls.add(3);

        float minRoll = diceRollStats.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(5F));
    }

    @Test
    public void givenAListThatContainsAnUnOrderedEvenNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheSumOfTheTwoMiddleRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(6);
        rolls.add(4);
        rolls.add(3);
        rolls.add(5);

        float minRoll = diceRollStats.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(4.5F));
    }
}