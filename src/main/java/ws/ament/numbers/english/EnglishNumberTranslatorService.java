package ws.ament.numbers.english;

import ws.ament.numbers.NumberTranslatorService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableMap;

/**
 * Translates input numerical values into their english language equivalents
 */
public class EnglishNumberTranslatorService implements NumberTranslatorService {
    private final Map<String, String> digits;
    private final Map<String, String> tens;

    public EnglishNumberTranslatorService() throws IOException {
        final Properties digitsProperties = new Properties();
        try(InputStream inputStream= getResourceAsStream("/specialNumbers.properties")) {
            digitsProperties.load(inputStream);
            this.digits = toMap(digitsProperties);
        }
        final Properties tensProperties = new Properties();
        try(InputStream inputStream = getResourceAsStream("/tens.properties")) {
            tensProperties.load(inputStream);
            this.tens = toMap(tensProperties);
        }
    }

    @Override
    public EnglishNumberTranslator createTranslator(int number) {
        return new EnglishNumberTranslator(number, tens, digits);
    }

    private InputStream getResourceAsStream(String resource) {
        return EnglishNumberTranslatorService.class.getResourceAsStream(resource);
    }

    private static Map<String, String> toMap(Properties properties) {
        return unmodifiableMap(properties.entrySet().stream().collect(Collectors.toConcurrentMap(e -> e.getKey().toString(),
                e-> e.getValue().toString())));
    }
}
