package gs.dice.diceroller.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DiceRollStatCalculatorTest {

    private DiceRollStatCalculator diceRollStatCalculator;

    @Before
    public void setup() {
        diceRollStatCalculator = new DiceRollStatCalculator();
    }

    @Test
    public void givenAListOfRollResultsThatContainsASingleRoll_thenTheMeanIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);

        float meanOfRolls = diceRollStatCalculator.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(5F));
    }

    @Test
    public void givenAListThatContainsAnOrderedEvenNumberOfRollResults_thenTheMeanIsReportedTheAverageOfTheRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(3);

        float meanOfRolls = diceRollStatCalculator.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(2F));
    }

    @Test
    public void givenAListThatContainsAnUnorderedEvenNumberOfRollResults_thenTheMeanIsReportedTheAverageOfTheRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);
        rolls.add(1);
        rolls.add(5);
        rolls.add(6);

        float meanOfRolls = diceRollStatCalculator.determineRollsMean(rolls);

        assertThat(meanOfRolls, equalTo(4F));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheSumIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);

        int sumOfRolls = diceRollStatCalculator.determineRollsSum(rolls);

        assertThat(sumOfRolls, equalTo(4));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheSumOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(2);
        rolls.add(3);

        int sumOfRolls = diceRollStatCalculator.determineRollsSum(rolls);

        assertThat(sumOfRolls, equalTo(5));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMaxIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(6);

        int maxRoll = diceRollStatCalculator.maxRoll(rolls);

        assertThat(maxRoll, equalTo(6));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheMaxOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);
        rolls.add(3);

        int maxRoll = diceRollStatCalculator.maxRoll(rolls);

        assertThat(maxRoll, equalTo(5));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMinIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(2);

        int minRoll = diceRollStatCalculator.minRoll(rolls);

        assertThat(minRoll, equalTo(2));
    }

    @Test
    public void givenAListOfMoreThanOneRollResult_thenTheMinOfTheRollResultsIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(4);
        rolls.add(2);

        int minRoll = diceRollStatCalculator.minRoll(rolls);

        assertThat(minRoll, equalTo(2));
    }

    @Test
    public void givenAListThatContainsASingleRollResult_thenTheMedianIsReportedBeingEqualToTheRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);

        float minRoll = diceRollStatCalculator.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(3F));
    }

    @Test
    public void givenAListThatContainsAnOrderedOddNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheMiddleRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);
        rolls.add(5);
        rolls.add(6);

        float minRoll = diceRollStatCalculator.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(5F));
    }

    @Test
    public void givenAListThatContainsAnOrderedEvenNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheSumOfTheTwoMiddleRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(3);
        rolls.add(4);
        rolls.add(5);
        rolls.add(6);

        float minRoll = diceRollStatCalculator.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(4.5F));
    }

    @Test
    public void givenAListThatContainsAnUnOrderedOddNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheMiddleRoll() {
        List<Integer> rolls = new ArrayList();
        rolls.add(5);
        rolls.add(6);
        rolls.add(3);

        float minRoll = diceRollStatCalculator.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(5F));
    }

    @Test
    public void givenAListThatContainsAnUnOrderedEvenNumberOfRollResults_thenTheMedianIsReportedBeingEqualToTheSumOfTheTwoMiddleRolls() {
        List<Integer> rolls = new ArrayList();
        rolls.add(6);
        rolls.add(4);
        rolls.add(3);
        rolls.add(5);

        float minRoll = diceRollStatCalculator.determineRollsMedian(rolls);

        assertThat(minRoll, equalTo(4.5F));
    }

    @Test
    public void givenAListOfRollResults_forADieOf3Sides_thenASummationListOfLengthMatchingTheNumberOfPossibleValuesIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(2);
        rolls.add(3);

        int dieType = 3;

        Map summationOfPossibleRollValues = diceRollStatCalculator.rollValueOccurrence(rolls, dieType);

        assertThat(summationOfPossibleRollValues.size(), equalTo(dieType));
    }

    @Test
    public void givenAListOfRollResults_forADieOf3Sides_thenASummationListContainingOnlyKeysThatCanComeFromTheDieTypeIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(2);
        rolls.add(3);

        int dieType = 3;

        Map<Integer, Integer> summationOfPossibleRollValues = diceRollStatCalculator.rollValueOccurrence(rolls, dieType);


        Map<Integer, Integer> invalidKeys = summationOfPossibleRollValues.entrySet()
                .stream()
                .filter(k -> k.getKey() > dieType)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        assertThat(invalidKeys.size(), equalTo(0));

    }

    @Test
    public void givenAListOfRollResultsThatAreAllOneValue_thenASummationListContainingOnlyOneKeyWithANonZeroValueIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(1);
        rolls.add(1);

        int dieType = 3;

        Map<Integer, Integer> summationOfPossibleRollValues = diceRollStatCalculator.rollValueOccurrence(rolls, dieType);


        assertThat(summationOfPossibleRollValues.get(1), equalTo(3));
        assertThat(summationOfPossibleRollValues.get(2), equalTo(0));
        assertThat(summationOfPossibleRollValues.get(3), equalTo(0));
    }

    @Test
    public void givenAListOfRollResultsThatAreMoreThanOneValue_thenASummationListDetailingTheOccurrenceOfEachRollValueIsReturned() {
        List<Integer> rolls = new ArrayList();
        rolls.add(1);
        rolls.add(2);
        rolls.add(3);

        int dieType = 3;

        Map<Integer, Integer> summationOfPossibleRollValues = diceRollStatCalculator.rollValueOccurrence(rolls, dieType);

        assertThat(summationOfPossibleRollValues.get(1), equalTo(1));
        assertThat(summationOfPossibleRollValues.get(2), equalTo(1));
        assertThat(summationOfPossibleRollValues.get(3), equalTo(1));
    }
}