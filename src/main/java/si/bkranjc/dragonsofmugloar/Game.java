package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;

@Value.Immutable
public interface Game {
    int gameId();

    Knight knight();

    @JsonCreator
    @Nonnull
    static Game get(@JsonProperty("gameId") final int id,
                    @JsonProperty("knight") @Nonnull final Knight knight) {
        return ImmutableGame.builder()
                .gameId(id)
                .knight(knight)
                .build();
    }
}
