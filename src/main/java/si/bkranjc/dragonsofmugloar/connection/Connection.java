package si.bkranjc.dragonsofmugloar.connection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import si.bkranjc.dragonsofmugloar.Dragon;
import si.bkranjc.dragonsofmugloar.Game;
import si.bkranjc.dragonsofmugloar.GameStatus;
import si.bkranjc.dragonsofmugloar.Weather;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Helper for communication with the outside HTTP servers.
 * <p>
 * This class contains all the details of the connection to the outside world and parses the received messages.
 */
public class Connection implements Closeable {
    private static final String WEATHER_URL = "http://www.dragonsofmugloar.com/weather/api/report/{gameid}";
    private static final String GAME_URL = "http://www.dragonsofmugloar.com/api/game";
    private static final String PUT_URL = "http://www.dragonsofmugloar.com/api/game/{gameid}/solution";

    private static String getWeatherURL(int gameId) {
        return WEATHER_URL.replace("{gameid}", Integer.toString(gameId));
    }

    private static String getPutUrl(int gameId) {
        return PUT_URL.replace("{gameid}", Integer.toString(gameId));
    }

    private final CloseableHttpClient client;
    private final ObjectMapper jsonMapper;
    private final XmlMapper xmlMapper;

    public Connection() {
        this.client = HttpClients.createDefault();
        this.jsonMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Retrieves a new game from the game server.
     *
     * @return The game information to be used for the dragon assignment.
     * @throws IOException If the connection is broken or the result cannot be parsed.
     */
    public Game getGame() throws IOException {
        final HttpGet request = new HttpGet(GAME_URL);
        final HttpResponse response = client.execute(request);
        final InputStream inp = response.getEntity().getContent();
        return jsonMapper.readValue(inp, Game.class);
    }

    /**
     * Retrieves the weather report.
     *
     * @param game Information on the game for which the weather shall be retrieved.
     * @return Weather on the day of the battle.
     * @throws IOException If the connection is broken or the result cannot be parsed.
     */
    public Weather getWeather(final Game game) throws IOException {
        final HttpGet request = new HttpGet(getWeatherURL(game.gameId));
        final HttpResponse response = client.execute(request);
        return xmlMapper.readValue(response.getEntity().getContent(), WeatherReport.class).code;
    }

    /**
     * Sends the dragon to the battle.
     *
     * @param game   Game of the battle.
     * @param dragon Dragon to send to the battle if necessary.
     * @return The status of the battle.
     * @throws IOException If the connection is broken or the result cannot be parsed.
     */
    public GameStatus sendDragon(final Game game, final Optional<Dragon> dragon) throws IOException {
        final HttpPut put = new HttpPut(getPutUrl(game.gameId));
        put.setHeader("Content-Type", "application/json");
        if (dragon.isPresent()) {
            final Response r = new Response();
            r.dragon = dragon.get();
            put.setEntity(new StringEntity(jsonMapper.writeValueAsString(r)));
        }
        final HttpResponse response = client.execute(put);
        return jsonMapper.readValue(response.getEntity().getContent(), GameResult.class).status;
    }

    @Override
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
