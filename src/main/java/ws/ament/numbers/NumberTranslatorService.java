package ws.ament.numbers;

import ws.ament.numbers.english.EnglishNumberTranslator;

public interface NumberTranslatorService {
    EnglishNumberTranslator createTranslator(int number);
}
