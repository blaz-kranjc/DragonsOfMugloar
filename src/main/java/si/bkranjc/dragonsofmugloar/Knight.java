package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;

@Value.Immutable
public interface Knight {
    String name();

    int attack();

    int armor();

    int agility();

    int endurance();

    @JsonCreator
    static Knight get(@JsonProperty("name") String name,
                      @JsonProperty("attack") int attack,
                      @JsonProperty("armor") int armor,
                      @JsonProperty("agility") int agility,
                      @JsonProperty("endurance") int endurance) {
        return ImmutableKnight.builder()
                .name(name)
                .attack(attack)
                .armor(armor)
                .agility(agility)
                .endurance(endurance)
                .build();

    }
}
