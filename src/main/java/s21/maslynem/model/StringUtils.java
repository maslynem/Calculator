package s21.maslynem.model;

import java.util.Arrays;

class StringUtils {
    private static final String OPERATORS = "%*+-~/^";
    private static final String[] FUNCTIONS = {"acos", "asin", "atan", "cos", "ctg", "ln", "log", "sin", "sqrt", "tan"};
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    private StringUtils() { }

     static boolean isOperand(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

     static boolean isOperator(String string) {
        return string.length() == 1 && OPERATORS.contains(string);
    }

     static boolean isOpenBracket(String string) {
        return OPEN_BRACKET.equals(string);
    }

     static boolean isCloseBracket(String string) {
        return CLOSE_BRACKET.equals(string);
    }

     static boolean isFunction(String string) {
        return Arrays.binarySearch(FUNCTIONS, string) >= 0;
    }
}
