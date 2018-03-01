package gs.dice.diceroller.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class DieRoll {

    @NonNull
    private Integer dieType;

    @NonNull
    private Integer rollCount;
}
