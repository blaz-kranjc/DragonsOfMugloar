package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGame.class)
public interface Game {
    int gameId();
    Knight knight();
}
