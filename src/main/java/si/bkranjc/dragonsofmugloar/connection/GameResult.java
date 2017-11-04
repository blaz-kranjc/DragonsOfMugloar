package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.GameStatus;

import javax.annotation.Nonnull;

/**
 * Result of a battle.
 */
@Value.Immutable
interface GameResult {
    GameStatus status();

    String message();

    @Nonnull
    @JsonCreator
    static GameResult get(@JsonProperty("status") GameStatus status, @JsonProperty("message") String message) {
        return ImmutableGameResult.builder()
                .status(status)
                .message(message)
                .build();
    }
}
