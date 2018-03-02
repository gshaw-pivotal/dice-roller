package gs.dice.diceroller.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Builder
@Data
public class DieRoll {

    @NonNull
    private Integer dieType;

    @NonNull
    private Integer rollCount;

    private List<Integer> rollResults;
}
