package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.Dragon;

/**
 * Dragon fleet sent to the battle.
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableResponse.class)
interface Response {
    Dragon dragon();
}
