
package me.nui.mp.database;

import io.helidon.common.http.Http;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;
import io.helidon.webclient.WebClientResponse;
import io.helidon.webserver.WebServer;
import io.helidon.microprofile.server.Server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PokemonTest {
    private static final JsonBuilderFactory JSON_BUILDER = Json.createBuilderFactory(Collections.emptyMap());

    private static Server webServer;
    private static WebClient webClient;

    @BeforeAll
    public static void startTheServer() {
        webServer = Main.startServer();

        webClient = WebClient.builder()
                .baseUri("http://localhost:" + "8080")
                .addMediaSupport(JsonpSupport.create())
                .build();
    }

//    @AfterAll
//    public static void stopServer() throws Exception {
//        if (webServer != null) {
//            webServer.shutdown()
//                    .toCompletableFuture()
//                    .get(10, TimeUnit.SECONDS);
//        }
//    }

    @Test
    public void testPokemonTypes() {
        JsonArray array = webClient.get()
                .path("/type")
                .request(JsonArray.class)
                .await();

        assertThat(array.size(), is(18));
        assertThat(array.get(0).asJsonObject().getInt("id"), is(1));
        assertThat(array.get(0).asJsonObject().getString("name"), is("Normal"));
    }

    @Test
    public void testPokemons() {
        assertThat(getPokemonCount(), is(6));

        WebClientResponse response;
        JsonObject pokemon;

        pokemon = webClient.get()
                .path("/pokemon/1")
                .request(JsonObject.class)
                .await();
        assertThat(pokemon.getString("name"), is("Bulbasaur"));

        pokemon = webClient.get()
                .path("/pokemon/name/Charmander")
                .request(JsonObject.class)
                .await();
        assertThat(pokemon.getJsonNumber("type").intValue(), is(10));

        JsonObject json = JSON_BUILDER.createObjectBuilder()
                .add("id", 100)
                .add("type", 1)
                .add("name", "Test")
                .build();
        response = webClient.post()
                .path("/pokemon")
                .submit(json)
                .await();
        assertThat(response.status(), is(Http.Status.NO_CONTENT_204));
        assertThat(getPokemonCount(), is(7));

        response = webClient.delete()
                .path("/pokemon/100")
                .request()
                .await();
        assertThat(response.status(), is(Http.Status.NO_CONTENT_204));

        assertThat(getPokemonCount(), is(6));
    }

    @Test
    public void testMetricsHealth() {
        WebClientResponse response;

        response = webClient.get()
                .path("/health")
                .request()
                .await();
        assertThat(response.status(), is(Http.Status.OK_200));

        response = webClient.get()
                .path("/metrics")
                .request()
                .await();
        assertThat(response.status(), is(Http.Status.OK_200));
    }

    private int getPokemonCount() {
        JsonArray array = webClient.get()
                .path("/pokemon")
                .request(JsonArray.class)
                .await();
        return array.size();
    }
}
