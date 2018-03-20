package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.models.DieRollStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiceRollServiceTest {

    private DieRoll dieRoll;

    @Mock
    private DiceRollStats diceRollStats;

    @InjectMocks
    private DiceRollService diceRollService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenASingleDieTypeForOneRoll_returnsTheDieRollWithResultingSingleRoll() {
        int numberOfRolls = 1;

        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(numberOfRolls)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        assertThat(resultingDieRoll.getRollResults().size(), equalTo(numberOfRolls));
    }

    @Test
    public void givenASingleDieTypeForMoreThanOneRoll_returnsTheDieRollWithTheMatchingNumberOfRolls() {
        int numberOfRolls = 4;

        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(numberOfRolls)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        assertThat(resultingDieRoll.getRollResults().size(), equalTo(numberOfRolls));
    }

    @Test
    public void givenASingleDieTypeToGenerateRolls_callsTheStatsService() {
        int dieType = 6;

        when(diceRollStats.determineRollsMean(anyList())).thenReturn(1F);
        when(diceRollStats.determineRollsSum(anyList())).thenReturn(1);
        when(diceRollStats.determineRollsMedian(anyList())).thenReturn(1F);
        when(diceRollStats.maxRoll(anyList())).thenReturn(1);
        when(diceRollStats.minRoll(anyList())).thenReturn(1);
        when(diceRollStats.rollValueOccurrence(anyList(), eq(dieType))).thenReturn(new HashMap());

        dieRoll = DieRoll.builder()
                .dieType(dieType)
                .rollCount(1)
                .build();

        diceRollService.generateRolls(dieRoll);

        verify(diceRollStats).determineRollsMean(anyList());
        verify(diceRollStats).determineRollsSum(anyList());
        verify(diceRollStats).determineRollsMedian(anyList());
        verify(diceRollStats).maxRoll(anyList());
        verify(diceRollStats).minRoll(anyList());
        verify(diceRollStats).rollValueOccurrence(anyList(), eq(dieType));
    }

    @Test
    public void givenASingleDieTypeToGenerateRolls_returnsTheDieRollWithStatsOnTheResultOfSaidRolls() {
        int dieType = 6;

        Map<Integer, Integer> rollValueOccurrence = new HashMap();
        for (int count = 1; count <= dieType; count++) {
            rollValueOccurrence.put(count, 1);
        }

        when(diceRollStats.determineRollsMean(anyList())).thenReturn(1F);
        when(diceRollStats.determineRollsSum(anyList())).thenReturn(1);
        when(diceRollStats.determineRollsMedian(anyList())).thenReturn(1F);
        when(diceRollStats.maxRoll(anyList())).thenReturn(1);
        when(diceRollStats.minRoll(anyList())).thenReturn(1);
        when(diceRollStats.rollValueOccurrence(anyList(), eq(dieType))).thenReturn(rollValueOccurrence);

        dieRoll = DieRoll.builder()
                .dieType(dieType)
                .rollCount(3)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        DieRollStats resultingStats = resultingDieRoll.getDieRollStats();

        assertThat(resultingStats.getMean(), is(notNullValue()));
        assertThat(resultingStats.getSum(), is(notNullValue()));
        assertThat(resultingStats.getMedian(), is(notNullValue()));
        assertThat(resultingStats.getMax(), is(notNullValue()));
        assertThat(resultingStats.getMin(), is(notNullValue()));
        assertThat(resultingStats.getRollValueOccurrence().size(), equalTo(dieType));
    }

    @Test
    public void givenMultipleDieTypesToGenerateRolls_returnsAListWithTheSameNumberOfDieTypes() {
        int numberOfDieType = 3;
        int numberOfRolls = 4;
        List<Integer> dieTypeList = new ArrayList();
        dieTypeList.add(3);
        dieTypeList.add(4);
        dieTypeList.add(5);

        List<DieRoll> request = buildListOfDieRoll(numberOfDieType, dieTypeList, numberOfRolls);

        List<DieRoll> results = diceRollService.generateRolls(request);
        assertThat(results.size(), equalTo(numberOfDieType));
    }

    @Test
    public void givenMultipleDieTypesToGenerateRolls_returnsAListWithResultingRolls() {
        int numberOfDieType = 3;
        int numberOfRolls = 4;
        List<Integer> dieTypeList = new ArrayList();
        dieTypeList.add(3);
        dieTypeList.add(4);
        dieTypeList.add(5);

        List<DieRoll> request = buildListOfDieRoll(numberOfDieType, dieTypeList, numberOfRolls);

        List<DieRoll> results = diceRollService.generateRolls(request);
        for (int count = 0; count < numberOfDieType; count++) {
            assertThat(results.get(count).getRollResults().size(), equalTo(numberOfRolls));
        }
    }

    @Test
    public void givenMultipleDieTypesToGenerateRolls_returnsAListWithStats() {
        int numberOfDieType = 3;
        int numberOfRolls = 4;
        List<Integer> dieTypeList = new ArrayList();
        dieTypeList.add(3);
        dieTypeList.add(4);
        dieTypeList.add(5);

        when(diceRollStats.determineRollsMean(anyList())).thenReturn(1F);
        when(diceRollStats.determineRollsSum(anyList())).thenReturn(1);
        when(diceRollStats.determineRollsMedian(anyList())).thenReturn(1F);
        when(diceRollStats.maxRoll(anyList())).thenReturn(1);
        when(diceRollStats.minRoll(anyList())).thenReturn(1);

        when(diceRollStats.rollValueOccurrence(anyList(), eq(dieTypeList.get(0)))).thenReturn(buildRollValueOccurrenceMap(dieTypeList.get(0)));
        when(diceRollStats.rollValueOccurrence(anyList(), eq(dieTypeList.get(1)))).thenReturn(buildRollValueOccurrenceMap(dieTypeList.get(1)));
        when(diceRollStats.rollValueOccurrence(anyList(), eq(dieTypeList.get(2)))).thenReturn(buildRollValueOccurrenceMap(dieTypeList.get(2)));



        List<DieRoll> request = buildListOfDieRoll(numberOfDieType, dieTypeList, numberOfRolls);

        List<DieRoll> results = diceRollService.generateRolls(request);

        for (int count = 0; count < numberOfDieType; count++) {
            DieRollStats resultingStats = results.get(count).getDieRollStats();

            assertThat(resultingStats.getMean(), is(notNullValue()));
            assertThat(resultingStats.getSum(), is(notNullValue()));
            assertThat(resultingStats.getMedian(), is(notNullValue()));
            assertThat(resultingStats.getMax(), is(notNullValue()));
            assertThat(resultingStats.getMin(), is(notNullValue()));
            assertThat(resultingStats.getRollValueOccurrence().size(), equalTo(dieTypeList.get(count)));
        }
    }

    private List<DieRoll> buildListOfDieRoll(int numberOfDieType, List<Integer>dieType, int numberOfRolls) {
        List<DieRoll> dieRollRequestList = new ArrayList();

        for (int count = 0; count < numberOfDieType; count++) {
            dieRoll = DieRoll.builder()
                    .dieType(dieType.get(count))
                    .rollCount(numberOfRolls)
                    .build();

            dieRollRequestList.add(dieRoll);
        }

        return dieRollRequestList;
    }

    private Map<Integer, Integer> buildRollValueOccurrenceMap(int dieType) {
        Map<Integer, Integer> rollValueOccurrence = new HashMap();

        for (int count = 0; count < dieType; count++) {
            rollValueOccurrence.put(count + 1, 1);
        }

        return rollValueOccurrence;
    }
}
