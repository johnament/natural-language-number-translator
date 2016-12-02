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
import static org.mockito.Mockito.verify;
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
        Object message = englishNumberRoute.handle(request, response);

        verify400EmptyInput(message);
    }

    @Test
    public void shouldReturn400OnEmptyInput() throws Exception{
        when(request.queryParams("number")).thenReturn("");
        Object message = englishNumberRoute.handle(request, response);

        verify400EmptyInput(message);
    }

    @Test
    public void shouldReturn400OnNonNumericInput() throws Exception {
        String digits = "123abc";
        when(request.queryParams("number")).thenReturn(digits);
        Object message = englishNumberRoute.handle(request, response);

        verify400InvalidInput(message);
    }

    @Test
    public void shouldReturn400OnTooLargeInput() throws Exception {
        String digits = "1000000001";
        when(request.queryParams("number")).thenReturn(digits);
        Object message = englishNumberRoute.handle(request, response);

        verify400InvalidInput(message);
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
        verify(response).type("text/plain");
        verify(response).status(200);
    }

    private void verify400InvalidInput(Object message) {
        assertThat(message).isEqualTo("Invalid input");
        verify400Status();
    }


    private void verify400EmptyInput(Object message) {
        assertThat(message).isEqualTo("No input specified");
        verify400Status();
    }

    private void verify400Status() {
        verify(response).type("text/plain");
        verify(response).status(400);
    }
}