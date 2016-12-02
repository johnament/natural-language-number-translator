package ws.ament.numbers.rest;

import org.apache.log4j.BasicConfigurator;
import ws.ament.numbers.english.EnglishNumberTranslatorService;

import java.io.IOException;

import static spark.Spark.get;
import static spark.route.RouteOverview.enableRouteOverview;

public class RestServer {
    public static void main(String...args) throws IOException {
        // just use console logging for now
        BasicConfigurator.configure();
        enableRouteOverview();
        get("/numbers/en", new EnglishNumberRoute(new EnglishNumberTranslatorService()));
    }
}
