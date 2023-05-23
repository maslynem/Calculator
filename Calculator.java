import java.util.Stack;

public class Calculator {
    private static final String OPERATORS = "~+-*/^%";

    public static double calculate(String expression) {
        sortStation(expression.toLowerCase());

        return 0;
    }

    private static void sortStation(String expression) {
        Stack<String> stack = new Stack<>();
        String[] tokens = expression.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

        }
    }

    private static boolean isOperand(String string) {
        try {
            Double.valueOf(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static boolean isOperator(String string) {
        return string.length() == 1 && OPERATORS.contains(string);
    }

    private static boolean isFunction(String string) {
        return FunctionsEnum.MOD.getName().equals(string); // todo
    }
}
