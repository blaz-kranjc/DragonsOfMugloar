package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDragon.class)
public interface Dragon {
    int scaleThickness();
    int clawSharpness();
    int wingStrength();
    int fireBreath();
}
