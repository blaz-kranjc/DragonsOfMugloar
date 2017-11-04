package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.Weather;

/**
 * Report on the weather on the day of the battle.
 */
@Value.Immutable
interface WeatherReport {
    Weather code();

    @JsonCreator
    static WeatherReport get(@JsonProperty("code") Weather code) {
        return ImmutableWeatherReport.builder()
                .code(code)
                .build();
    }
}
