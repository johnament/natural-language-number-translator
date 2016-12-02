package ws.ament.numbers.rest;

import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;
import ws.ament.numbers.NumberTranslatorService;

public class EnglishNumberRoute implements Route{
    private static final String INVALID_INPUT = "Invalid input";
    private static final String NO_INPUT_SPECIFIED = "No input specified";

    private static final String NUMBER = "number";

    private final NumberTranslatorService numberTranslatorService;
    private static final int MAX_INPUT = 1_000_000_000;

    public EnglishNumberRoute(NumberTranslatorService numberTranslatorService) {
        this.numberTranslatorService = numberTranslatorService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String number = request.queryParams(NUMBER);
        if(StringUtils.isEmpty(number)) {
            throw new InvalidInputException(NO_INPUT_SPECIFIED);
        }
        else if(!StringUtils.isNumeric(number)) {
            throw new InvalidInputException(INVALID_INPUT);
        }
        try {
            Integer numberValue = Integer.parseInt(number);
            if (numberValue > MAX_INPUT) {
                throw new InvalidInputException(INVALID_INPUT);
            }
            return numberTranslatorService.createTranslator(numberValue).getCapitalizedTranslation();
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException(INVALID_INPUT,e);
        }
    }
}
