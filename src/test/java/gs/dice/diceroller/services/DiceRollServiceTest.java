package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;
import gs.dice.diceroller.models.DieRollStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    public void givenADieRollForOneRoll_returnsTheDieRollWithResultingSingleRoll() {
        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(1)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        assertThat(resultingDieRoll.getRollResults().size(), equalTo(1));
    }

    @Test
    public void givenADieRollForMoreThanOneRoll_returnsTheDieRollWithTheMatchingNumberOfRolls() {
        int numberOfRolls = 4;

        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(numberOfRolls)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        assertThat(resultingDieRoll.getRollResults().size(), equalTo(numberOfRolls));
    }

    @Test
    public void givenACallToGenerateRolls_callsTheStatsService() {

        when(diceRollStats.determineRollsMean(any())).thenReturn(1F);
        when(diceRollStats.determineRollsSum(any())).thenReturn(1);
        when(diceRollStats.determineRollsMedian(any())).thenReturn(1F);
        when(diceRollStats.maxRoll(any())).thenReturn(1);
        when(diceRollStats.minRoll(any())).thenReturn(1);

        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(1)
                .build();

        diceRollService.generateRolls(dieRoll);

        verify(diceRollStats).determineRollsMean(any());
        verify(diceRollStats).determineRollsSum(any());
        verify(diceRollStats).determineRollsMedian(any());
        verify(diceRollStats).maxRoll(any());
        verify(diceRollStats).minRoll(any());
    }

    @Test
    public void givenACallToGenerateRolls_returnsTheDieRollWithStatsOnTheResultOfSaidRolls() {
        when(diceRollStats.determineRollsMean(any())).thenReturn(1F);
        when(diceRollStats.determineRollsSum(any())).thenReturn(1);
        when(diceRollStats.determineRollsMedian(any())).thenReturn(1F);
        when(diceRollStats.maxRoll(any())).thenReturn(1);
        when(diceRollStats.minRoll(any())).thenReturn(1);

        dieRoll = DieRoll.builder()
                .dieType(6)
                .rollCount(3)
                .build();

        DieRoll resultingDieRoll = diceRollService.generateRolls(dieRoll);
        DieRollStats resultingStats = resultingDieRoll.getDieRollStats();

        assertThat(resultingStats.getMean(), is(notNullValue()));
        assertThat(resultingStats.getSum(), is(notNullValue()));
        assertThat(resultingStats.getMedian(), is(notNullValue()));
        assertThat(resultingStats.getMax(), is(notNullValue()));
        assertThat(resultingStats.getMin(), is(notNullValue()));
    }
}
