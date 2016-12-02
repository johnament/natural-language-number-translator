package ws.ament.numbers.rest.integration;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ament.numbers.rest.RestServer;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.stop;

public class NumberTranslatorServiceIntegrationTest {
    @BeforeClass
    public static void setupServer() throws Exception {
        RestServer.main();
        RestAssured.baseURI = "http://localhost:4567/";
    }
    @AfterClass
    public static void shutdownServer() throws Exception {
        stop();
    }
    @Test
    public void shouldReturn200OkWithValidInput() {
        get("/numbers/en?number=123").then()
                .statusCode(200)
                .body(equalTo("One hundred and twenty three"));
    }
    @Test
    public void shouldReturn400OnEmptyInput() {
        get("/numbers/en").then()
                .statusCode(400)
                .body(equalTo("No input specified"));
    }
    @Test
    public void shouldReturn400OnLargeInput() {
        get("/numbers/en?number=2000000000").then()
                .statusCode(400)
                .body(equalTo("Invalid input"));
    }

    @Test
    public void shouldReturn400OnInvalidInput() {
        get("/numbers/en?number=word").then()
                .statusCode(400)
                .body(equalTo("Invalid input"));
    }
}
