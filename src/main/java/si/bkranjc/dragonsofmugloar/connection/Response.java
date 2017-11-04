package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.Dragon;

import javax.annotation.Nonnull;

/**
 * Dragon fleet sent to the battle.
 */
@Value.Immutable
interface Response {
    @JsonProperty("dragon")
    Dragon dragon();

    static Response get(@Nonnull Dragon d) {
        return ImmutableResponse.builder()
                .dragon(d)
                .build();
    }
}
