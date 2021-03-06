package gs.dice.diceroller.models;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class DieRollStats {
    private int sum;

    private int max;

    private int min;

    private float mean;

    private float median;

    private Map<Integer, Integer> rollValueOccurrence;
}
