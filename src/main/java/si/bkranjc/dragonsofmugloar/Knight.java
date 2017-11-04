package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableKnight.class)
public interface Knight {
    String name();
    int attack();
    int armor();
    int agility();
    int endurance();
}
