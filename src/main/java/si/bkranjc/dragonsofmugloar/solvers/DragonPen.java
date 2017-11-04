package si.bkranjc.dragonsofmugloar.solvers;

import si.bkranjc.dragonsofmugloar.Dragon;
import si.bkranjc.dragonsofmugloar.Knight;
import si.bkranjc.dragonsofmugloar.Weather;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Static factory of dragons to send into a fight.
 */
public class DragonPen {
    private DragonPen() { /* Only contains static methods */ }

    /**
     * Factory method that returns the dragon to send into a battle based on the weather and the knight that is
     * attacking.
     *
     * @param knight  The knight that will come to battle.
     * @param weather The weather forecast for the time of the battle.
     * @return A dragon that will beat the knight or no dragon.
     */
    @Nonnull
    public static Optional<Dragon> callDragon(final @Nonnull Knight knight, @Nullable Weather weather) {
        if (weather == null) {
            // We don't have valid information on the weather, we are using the normal one
            throw new IllegalArgumentException("We never saw a weather like this before!");
        }
        switch (weather) {
            case NORMAL:
                return Optional.of(DragonPicker.NORMAL.getDragon(knight));
            case FOG:
                // It seems that dragons have an easy time killing the knights in the fog.
                // We are sending our normal dragons to train them for other weathers.
                return Optional.of(DragonPicker.NORMAL.getDragon(knight));
            case STORM:
                // Stormy weather killed a lot of our dragons. It seems that the knights are no more lucky than we are.
                return Optional.empty();
            case FLOOD:
                return Optional.of(DragonPicker.BOAT_SLAYER.getDragon(knight));
            case LONG_DRY:
                return Optional.of(DragonPicker.ZEN_DRAGON.getDragon(knight));
        }
        throw new IllegalArgumentException("Our pen experienced an logical error. This should never happen.");
    }
}
