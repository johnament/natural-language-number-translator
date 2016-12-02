package ws.ament.numbers;

import org.apache.commons.lang3.StringUtils;

public interface NumberTranslator {
    /**
     * Translates the number represented by this class into a language based phrase/sentence
     *
     * @return
     */
    String translate();

    default String getCapitalizedTranslation() {
        return StringUtils.capitalize(translate());
    }
}
