package s21.maslynem.model.utils;

import java.util.Arrays;

public class StringUtils {
    private static final String OPERATORS = "%*+-~/^";
    private static final String[] FUNCTIONS = {"acos", "asin", "atan", "cos", "ctg", "ln", "log", "sin", "sqrt", "tan"};
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    private StringUtils() { }

     static public boolean isOperand(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

     static public boolean isOperator(String string) {
        return string.length() == 1 && OPERATORS.contains(string);
    }

     static public boolean isOpenBracket(String string) {
        return OPEN_BRACKET.equals(string);
    }

     static public boolean isCloseBracket(String string) {
        return CLOSE_BRACKET.equals(string);
    }

     static public boolean isFunction(String string) {
        return Arrays.binarySearch(FUNCTIONS, string) >= 0;
    }
}
