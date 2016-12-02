package ws.ament.numbers.english;

import org.apache.commons.lang3.StringUtils;
import ws.ament.numbers.NumberTranslator;

import java.util.Map;

/**
 * A version of NumberTranslator that handles the general "Middle America" lexicons for English translation of
 * numeric values into their written word forms.
 */
public class EnglishNumberTranslator implements NumberTranslator {
    private final int number;
    private final Map<String,String> tens;
    private final Map<String,String> digits;
    private final String strValue;

    EnglishNumberTranslator(int number, Map<String,String> tens, Map<String,String> digits) {
        this.number = number;
        this.tens = tens;
        this.digits = digits;
        this.strValue = String.valueOf(number);
    }

    @Override
    public String translate() {
        if(this.number < 0) {
            return "negative "+new EnglishNumberTranslator(-1 * this.number, tens, digits).translate();
        }
        else {
            return parseNumericString(number, strValue, true);
        }
    }

    private String parseNumericString(int number, String strValue, boolean includeOptionalAnd) {
        switch(strValue.length()) {
            case 1:
                return calculateBasicNumber(strValue);
            case 2:
                return calculateTwoDigit(strValue, number);
            case 3:
                return calculateThreeDigit(strValue, includeOptionalAnd);
            case 4:
            case 5:
            case 6:
                return parsePrefixSuffixByLength(strValue, 3, "thousand");
            case 7:
            case 8:
            case 9:
                return parsePrefixSuffixByLength(strValue, 6, "million");
            case 10:
                return parsePrefixSuffixByLength(strValue, 9, "billion");
        }
        return null;
    }

    private String calculateThreeDigit(String strValue, boolean includeOptionalAnd) {
        char[] digits = strValue.toCharArray();
        String prefixString = String.valueOf(digits[0]);
        int prefixNumber = Integer.valueOf(prefixString);

        String prefix = "";
        if(prefixNumber > 0) {
            prefix = calculateBasicNumber(prefixString) + " hundred";
        }
        String remainder = String.valueOf(new char[]{digits[1],digits[2]});
        int twoDigitNumber = Integer.parseInt(remainder);
        if(twoDigitNumber > 0) {
            String suffix = calculateTwoDigit(remainder, twoDigitNumber);
            if(includeOptionalAnd) {
                if (StringUtils.isEmpty(prefix)) {
                    prefix = "and";
                }
                else {
                    prefix = prefix + " and";
                }

            }
            return combine(prefix, suffix);
        }
        else {
            return prefix;
        }
    }

    private String calculateTwoDigit(String strValue, int number) {
        if(number > 19) {
            char[] digits = strValue.toCharArray();
            String prefix = calculateTwoDigitFirstWord(String.valueOf(digits[0]));
            String suffix = calculateBasicNumber(String.valueOf(digits[1]));
            return combine(prefix, suffix);
        }
        else {
            return calculateBasicNumber(strValue);
        }
    }

    /**
     * Splits the given input string based on the number of characters.
     * For instance, if the string is 6 characters and the numberOfCharacters is 4, you'll get back a string array where the first entry is the first 2 digits of the string, and the second entry is the final 4 characters
     * @param string
     * @param numberOfCharacters
     * @return
     */
    private String[] splitNumber(String string, int numberOfCharacters) {
        if(string.length() <= numberOfCharacters) {
            throw new RuntimeException("String "+string+" cannot be split into characters "+numberOfCharacters);
        }
        String prefix = string.substring(0, string.length() - numberOfCharacters);
        String suffix = string.replaceFirst(prefix, "");
        return new String[]{prefix, suffix};
    }

    private String calculateTwoDigitFirstWord(String digit) {
        return tens.get(digit);
    }

    /**
     * A basic number is a numeric value that directly translates into a word that is not compound, e.g. 0-19
     * @param number
     * @return
     */
    private String calculateBasicNumber(String number) {
        if(number.length() == 2 && number.charAt(0) == '0') {
            return digits.get(String.valueOf(number.charAt(1)));
        }
        return digits.get(number);
    }

    /**
     * For a given numeric string, constructs an english language representation by taking the outermost value and combining it with the derived right most values
     * This is a recursive method
     *
     * @param stringValue  - The original value to be converted
     * @param length - The length being considered for the current calculation.  The remaining value will be reprocessed.
     * @param name - The name to be added to the number in the prefix section
     * @return An english representation of the number
     */
    private String parsePrefixSuffixByLength(String stringValue, int length, String name) {
        String[] prefixSuffix = splitNumber(stringValue, length);
        String prefix = parseNumericString(Integer.valueOf(prefixSuffix[0]), prefixSuffix[0], false) + " " + name;
        String suffix = parseNumericString(Integer.valueOf(prefixSuffix[1]), prefixSuffix[1], true);
        return combine(prefix, suffix);
    }

    /**
     * Merges the prefix and suffix portions of the number to form the words for the number.  Handles cases such as prefix is empty or suffix is empty.
     * @param prefix
     * @param suffix
     * @return
     */
    private String combine(String prefix, String suffix) {
        if(StringUtils.isEmpty(prefix)) {
            return suffix;
        }
        else if(StringUtils.isEmpty(suffix)) {
            return prefix;
        }
        else {
            return String.format("%s %s", prefix, suffix);
        }
    }
}
