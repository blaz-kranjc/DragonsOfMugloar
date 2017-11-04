package si.bkranjc.dragonsofmugloar;

import si.bkranjc.dragonsofmugloar.connection.Connection;
import si.bkranjc.dragonsofmugloar.solvers.DragonPen;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

/**
 * A solution to the <a href="https://www.dragonsofmugloar.com/">Dragons of Mugloar challenge</a>.
 * <p>
 * The program retrieves a game from the REST API and returns the solution to it.
 */
class DragonsOfMugloarMain {

    private static void printHelp() {
        System.out.println("Usage: dragonsOfMugloar <numberOfGames>");
    }

    @Nonnull
    private static String verboseGameStatus(@Nonnull final Game game,
                                            @Nonnull final GameStatus status,
                                            @Nonnull final Weather weather,
                                            @Nonnull final Optional<Dragon> dragon) {
        StringBuilder sb = new StringBuilder();
        sb.append(game.knight().name());
        sb.append(" came to the battle");
        switch (weather) {
            case FOG:
                sb.append(" in a fog like nobody saw in a decades. ");
                break;
            case STORM:
                sb.append(" in an epic storm. ");
                break;
            case NORMAL:
                sb.append(". ");
                break;
            case LONG_DRY:
                sb.append(" during the driest year. ");
                break;
            case FLOOD:
                sb.append("with his umbrella boat. ");
                break;
        }

        if (dragon.isPresent()) {
            sb.append("Our dragon pickers selected a mighty dragon for the battle. ");
        } else {
            sb.append("Our dragon pickers decided that we should leave the knight alone. ");
        }

        if (status == GameStatus.DEFEAT) {
            sb.append("We were defeated!");
        } else if (status == GameStatus.VICTORY) {
            sb.append("We were victorious!");
        }

        return sb.toString();
    }

    private static class ScoreTally {
        private int games = 0;
        private int victories = 0;

        void mark(@Nonnull final GameStatus status) {
            ++games;
            if (status == GameStatus.VICTORY) {
                ++victories;
            }
        }

        double getWinRate() {
            return (double) victories / games;
        }

        int getGames() {
            return games;
        }
    }

    /**
     * A program that solves the <a href="https://www.dragonsofmugloar.com/">Dragons of Mugloar challenge</a>.
     * <p>
     * This program expects one argument - the number of games. The program than retrieves and tries to solve the
     * specified number or games. The result of each game is counted and a final success rate is displayed to the user.
     */
    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            printHelp();
            System.exit(1);
        }

        int gamesToPlay = 0;
        try {
            gamesToPlay = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            printHelp();
            System.exit(2);
        }

        ScoreTally tally = new ScoreTally();
        try (Connection connection = new Connection()) {
            for (int i = 0; i < gamesToPlay; ++i) {
                Game game = connection.getGame();
                Weather weather = connection.getWeather(game);

                try {
                    Optional<Dragon> dragon = DragonPen.callDragon(game.knight(), weather);
                    GameStatus status = connection.sendDragon(game, dragon);

                    if (status == null) {
                        System.out.println("It seems that results of the battle were inconclusive. " +
                                "But we are perfectionists, we are counting this as a defeat.");
                        tally.mark(GameStatus.DEFEAT);
                        continue;
                    }
                    tally.mark(status);
                    System.out.println(verboseGameStatus(game, status, weather, dragon));
                } catch (IllegalArgumentException e) {
                    System.out.println("Our dragon pickers were surprised by " + game.knight().name() + ". They said: "
                            + e.getMessage());
                    System.out.println(game);
                    tally.mark(GameStatus.DEFEAT);
                }
            }
        } catch (IOException e) {
            System.out.println("There seems to be a problem with the knight supply. Please try again later.");
            System.exit(3);
        }

        System.out.println();
        System.out.print("The knight hunting season ended with ");
        System.out.print(tally.getWinRate() * 100);
        System.out.print("% of victories in ");
        System.out.print(tally.getGames());
        System.out.println(" battles.");
    }
}
