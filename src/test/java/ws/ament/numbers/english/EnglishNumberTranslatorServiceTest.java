package ws.ament.numbers.english;

import org.junit.Before;
import org.junit.Test;
import ws.ament.numbers.NumberTranslatorService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EnglishNumberTranslatorServiceTest {
    private NumberTranslatorService numberTranslator;
    // new favorite site http://www.grammar-monster.com/lessons/numbers_how_to_write_in_full.htm
    @Before
    public void initService() throws IOException{
        this.numberTranslator = new EnglishNumberTranslatorService();
    }

    @Test
    public void shouldTranslateInputs() {
        Map<Integer, String> inputOutput = new HashMap<>();
        inputOutput.put(0, "zero");
        inputOutput.put(1, "one");
        inputOutput.put(19, "nineteen");
        inputOutput.put(42, "forty two");
        inputOutput.put(100, "one hundred");
        inputOutput.put(102, "one hundred and two");
        inputOutput.put(109, "one hundred and nine");
        inputOutput.put(123, "one hundred and twenty three");
        inputOutput.put(1000, "one thousand");
        inputOutput.put(1023, "one thousand and twenty three");
        inputOutput.put(1123, "one thousand one hundred and twenty three");
        inputOutput.put(10123, "ten thousand one hundred and twenty three");
        inputOutput.put(100000, "one hundred thousand");
        inputOutput.put(123456, "one hundred twenty three thousand four hundred and fifty six");
        inputOutput.put(1123456, "one million one hundred twenty three thousand four hundred and fifty six");
        inputOutput.put(1897123456, "one billion eight hundred ninety seven million one hundred twenty three thousand four hundred and fifty six");

        inputOutput.entrySet()
                .forEach(e -> assertThat(numberTranslator.createTranslator(e.getKey()).translate())
                            .as("Expected value for %d", e.getKey())
                            .isEqualTo(e.getValue()));
    }

    @Test
    public void shouldCapitalizedTranslatedInputs() {
        Map<Integer, String> inputOutput = new HashMap<>();
        inputOutput.put(0, "Zero");
        inputOutput.put(1, "One");
        inputOutput.put(1123456, "One million one hundred twenty three thousand four hundred and fifty six");
        inputOutput.entrySet()
                .forEach(e -> assertThat(numberTranslator.createTranslator(e.getKey()).getCapitalizedTranslation())
                        .as("Expected %d to be printed as %s", e.getKey(), e.getValue())
                        .isEqualTo(e.getValue()));
    }

    @Test
    public void shouldHandleNegativeNumbers() {
        Map<Integer, String> inputOutput = new HashMap<>();
        inputOutput.put(-0, "Zero");
        inputOutput.put(-1, "Negative one");
        inputOutput.put(-10123, "Negative ten thousand one hundred and twenty three");
        inputOutput.entrySet()
                .forEach(e -> assertThat(numberTranslator.createTranslator(e.getKey()).getCapitalizedTranslation())
                        .as("Expected %d to be printed as %s", e.getKey(), e.getValue())
                        .isEqualTo(e.getValue()));
    }
}