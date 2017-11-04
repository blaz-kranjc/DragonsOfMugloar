package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum Weather {
    // TODO this codes seem like they could spell something in the right order...
    LONG_DRY("T E"),
    NORMAL("NMR"),
    STORM("SRO"),
    FLOOD("HVA"),
    FOG("FUNDEFINEDG");

    private final String code;

    Weather(final @Nonnull String code) {
        this.code = code;
    }

    @JsonCreator
    @Nullable
    public static Weather of(final String str) {
        for (Weather weather : values()) {
            if (weather.code.equals(str)) {
                return weather;
            }
        }
        return null;
    }
}
