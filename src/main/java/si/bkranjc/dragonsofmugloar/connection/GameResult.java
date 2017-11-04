package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.GameStatus;

/**
 * Result of a battle.
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableGameResult.class)
interface GameResult {
    GameStatus status();
    String message();
}
