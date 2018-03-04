package gs.dice.diceroller.services;

import gs.dice.diceroller.models.DieRoll;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

import static org.junit.Assert.*;

public class DiceRollServiceTest {

    private DieRoll dieRoll;

    private DiceRollService diceRollService;

    @Before
    public void setup() {
        diceRollService = new DiceRollService();
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
}
