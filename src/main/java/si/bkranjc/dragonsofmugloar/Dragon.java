package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;

@Value.Immutable
public interface Dragon {
    @JsonProperty("scaleThickness")
    int scaleThickness();

    @JsonProperty("clawSharpness")
    int clawSharpness();

    @JsonProperty("wingStrength")
    int wingStrength();

    @JsonProperty("fireBreath")
    int fireBreath();
}
