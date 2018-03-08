package gs.dice.diceroller.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DieRollStats {
    private int sum;

    private int max;

    private int min;

    private float mean;

    private float median;
}
