package ws.ament.numbers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import ws.ament.numbers.NumberTranslator;
import ws.ament.numbers.NumberTranslatorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnglishNumberRouteTest {
    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private NumberTranslatorService numberTranslatorService;

    @Mock
    private NumberTranslator numberTranslator;

    @InjectMocks
    private EnglishNumberRoute englishNumberRoute;

    @Test
    public void shouldReturn400OnNullInput() throws Exception{
        when(request.queryParams("number")).thenReturn(null);
        assertThatThrownBy(() -> englishNumberRoute.handle(request, response))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageMatching("No input specified");
    }

    @Test
    public void shouldReturn400OnEmptyInput() throws Exception{
        when(request.queryParams("number")).thenReturn("");
        assertThatThrownBy(() -> englishNumberRoute.handle(request, response))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageMatching("No input specified");
    }

    @Test
    public void shouldReturn400OnNonNumericInput() throws Exception {
        String digits = "123abc";
        when(request.queryParams("number")).thenReturn(digits);
        assertThatThrownBy(() -> englishNumberRoute.handle(request, response))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageMatching("Invalid input");
    }

    @Test
    public void shouldReturn400OnTooLargeInput() throws Exception {
        String digits = "1000000001";
        when(request.queryParams("number")).thenReturn(digits);
        assertThatThrownBy(() -> englishNumberRoute.handle(request, response))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageMatching("Invalid input");
    }

    @Test
    public void shouldReturnParsedInputAsOutput() throws Exception {
        String expectedOutput = "Won too tree";
        String digits = "123";
        when(request.queryParams("number")).thenReturn(digits);
        when(numberTranslatorService.createTranslator(123)).thenReturn(numberTranslator);
        when(numberTranslator.getCapitalizedTranslation()).thenReturn(expectedOutput);

        Object message = englishNumberRoute.handle(request, response);

        assertThat(message).isEqualTo(expectedOutput);
    }

    @Test
    public void shouldHandleLongInput() throws Exception {
        String digits = "1242523625325";
        when(request.queryParams("number")).thenReturn(digits);

        assertThatThrownBy(() -> englishNumberRoute.handle(request, response))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageMatching("Invalid input");
    }

}