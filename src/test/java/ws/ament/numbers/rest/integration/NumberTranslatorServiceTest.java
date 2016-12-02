package ws.ament.numbers.rest.integration;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ament.numbers.rest.RestServer;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.stop;

public class NumberTranslatorServiceTest {
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
                .contentType("text/plain")
                .body(equalTo("One hundred and twenty three"));
    }
    @Test
    public void shouldReturn400OnEmptyInput() {
        get("/numbers/en").then()
                .statusCode(400)
                .contentType("text/plain")
                .body(equalTo("No input specified"));
    }
}
