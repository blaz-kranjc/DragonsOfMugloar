package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import si.bkranjc.dragonsofmugloar.Weather;

/**
 * Report on the weather on the day of the battle.
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableWeatherReport.class)
interface WeatherReport {
    Weather code();
}
