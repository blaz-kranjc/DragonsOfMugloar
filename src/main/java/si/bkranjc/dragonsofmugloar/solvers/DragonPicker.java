package si.bkranjc.dragonsofmugloar.solvers;

import si.bkranjc.dragonsofmugloar.Dragon;
import si.bkranjc.dragonsofmugloar.Knight;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Selection of dragon picking algorithms.
 */
enum DragonPicker {
    /**
     * Sends a dragon that is by stronger than is by +2 stronger then the knight in the knight's highest "connected"
     * stat. The points are taken from the next higher stats (one from each).
     * <p>
     * This expects the knight to have at most 8 points in the highest stat.
     * <p>
     * Connected stats:
     * attack <=> scaleThickness
     * armor <=> clawSharpness
     * agility <=> wingStrength
     * endurance <=> fireBreath
     */
    NORMAL(
            k -> {
                int[] stats = new int[]{k.attack, k.armor, k.agility, k.endurance};
                // Indexes of the sorted stats.
                int[] statsSortedIndexes = IntStream.range(0, stats.length)
                        .boxed()
                        .sorted(Comparator.comparingInt((Integer i) -> stats[i]).reversed())
                        .mapToInt(i -> i)
                        .toArray();

                // Check if this knight fits the preconditions of this solver
                if (stats[statsSortedIndexes[0]] > 8) {
                    throw new IllegalArgumentException(
                            k.name +
                                    "'s max stat is unexpectedly high. " +
                                    "No dragon in our pen can defeat him.");
                }

                // If the preconditions are satisfied then at least three largest stats are greater than zero.
                stats[statsSortedIndexes[0]] += 2;
                stats[statsSortedIndexes[1]] -= 1;
                stats[statsSortedIndexes[2]] -= 1;

                return getDragonFomStatArray(stats);
            }
    ),
    /**
     * Sends a dragon that is adapted to kill boats and unable to breathe fire.
     */
    BOAT_SLAYER(
            k -> {
                Dragon d = new Dragon();
                d.clawSharpness = 10;
                d.fireBreath = 0;
                d.scaleThickness = 5;
                d.wingStrength = 5;
                return d;
            }
    ),
    /**
     * Sends a dragon with equal distribution of the stats.
     */
    ZEN_DRAGON(
            k -> {
                Dragon d = new Dragon();
                d.clawSharpness = 5;
                d.fireBreath = 5;
                d.scaleThickness = 5;
                d.wingStrength = 5;
                return d;
            }
    );

    private final Solver solver;

    DragonPicker(final Solver solver) {
        this.solver = solver;
    }

    Dragon getDragon(final Knight k) {
        return solver.getDragon(k);
    }

    @FunctionalInterface
    private interface Solver {
        Dragon getDragon(Knight k);
    }

    /**
     * Picks dragon from the stats array.
     *
     * @param array Array of dragon stats. This array must be of size 4 and the order of the stats is:
     *              scaleThickness, clawSharpness, wingStrength, fireBreath.
     * @return Dragon with specified name.
     */
    private static Dragon getDragonFomStatArray(final int[] array) {
        Dragon d = new Dragon();
        d.scaleThickness = array[0];
        d.clawSharpness = array[1];
        d.wingStrength = array[2];
        d.fireBreath = array[3];
        return d;
    }

}
