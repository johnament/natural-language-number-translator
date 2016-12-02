package ws.ament.numbers.rest;

import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;
import ws.ament.numbers.NumberTranslatorService;

public class EnglishNumberRoute implements Route{
    private static final String TEXT_PLAIN = "text/plain";
    private final NumberTranslatorService numberTranslatorService;
    private static final int MAX_INPUT = 1_000_000_000;

    public EnglishNumberRoute(NumberTranslatorService numberTranslatorService) {
        this.numberTranslatorService = numberTranslatorService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String number = request.queryParams("number");
        if(StringUtils.isEmpty(number)) {
            return400(response);
            return "No input specified";
        }
        else if(!StringUtils.isNumeric(number)) {
            return400(response);
            return "Invalid input";
        }
        Integer numberValue = Integer.parseInt(number);
        if(numberValue > MAX_INPUT) {
            return400(response);
            return "Invalid input";
        }
        String message = numberTranslatorService.createTranslator(numberValue).getCapitalizedTranslation();
        response.status(200);
        response.type(TEXT_PLAIN);
        return message;
    }

    private void return400(Response response) {
        response.status(400);
        response.type(TEXT_PLAIN);
    }
}
