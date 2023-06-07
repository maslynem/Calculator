package s21.maslynem.model;

import java.util.*;

public class Calculator {
    private static final String OPERATORS = "%*+-/^";
    private static final String[] FUNCTIONS = {"acos", "asin", "atan", "cos", "ctg", "ln", "log", "sin", "sqrt", "tan"};
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    public static double calculate(String expression) {
        String newExpression = prepareExpression(expression);
        List<String> postfix = castToPostfix(newExpression);
        return calculateByPostfix(postfix);
    }

    private static String prepareExpression(String expression) {
        String newExpression = expression
                .toLowerCase()
                .replaceAll(" ", "")
                .replaceAll("π", String.valueOf(Math.PI))
                .replaceAll("√", "sqrt")
                .replaceAll(",", ".")
                .replaceAll("\\(-", "(0-")
                .replaceAll("\\.-", ".0-")
                .replaceAll("\\(\\+", "(");
        char ch = newExpression.charAt(0);
        if (ch == '-' || ch == '+') {
            newExpression = "0" + newExpression;
        }
        return newExpression;
    }

    private static List<String> castToPostfix(String expression) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(expression, OPERATORS + "()e", true);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token.equals("e")) {
                String operand = postfix.get(postfix.size() - 1);
                postfix.remove(postfix.size() - 1);
                postfix.add(operand + token + tokens.nextToken() + tokens.nextToken());
            } else if (isOperand(token)) {
                postfix.add(token);
            } else if (isFunction(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek()) &&
                        (getOperatorPriority(stack.peek()) > getOperatorPriority(token) ||
                                getOperatorPriority(stack.peek()) == getOperatorPriority(token) && token.equals("^"))) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            } else if (isOpenBracket(token)) {
                stack.push(token);
            } else if (isCloseBracket(token)) {
                try {
                    while (!isOpenBracket(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                    stack.pop();
                    if (!stack.empty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } catch (EmptyStackException exception) {
                    throw new WrongExpressionException("Wrong count of brackets.");
                }
            } else {
                throw new WrongExpressionException(String.format("[%s] is not available.", token));
            }
        }
        while (!stack.empty()) {
            String token = stack.pop();
            if (isOpenBracket(token)) {
                throw new WrongExpressionException("Wrong count of brackets.");
            }
            postfix.add(token);
        }
        return postfix;
    }

    private static double calculateByPostfix(List<String> postfix) {
        try {
            Stack<Double> stack = new Stack<>();
            for (String token : postfix) {
                if (isOperand(token)) {
                    double value = Double.parseDouble(token);
                    if (Math.abs(0 - value) < 1e-7) {
                        value = 0;
                    }
                    stack.push(value);
                } else if (isOperator(token)) {
                    double result = executeOperator(stack, token);
                    stack.push(result);
                } else {
                    double result = executeFunction(stack, token);
                    stack.push(result);

                }
            }
            return stack.pop();
        } catch (EmptyStackException exception) {
            throw new WrongExpressionException("Wrong expression");
        }
    }

    private static boolean isOperand(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static boolean isOperator(String string) {
        return string.length() == 1 && OPERATORS.contains(string);
    }

    private static boolean isOpenBracket(String string) {
        return OPEN_BRACKET.equals(string);
    }

    private static boolean isCloseBracket(String string) {
        return CLOSE_BRACKET.equals(string);
    }

    private static boolean isFunction(String string) {
        return Arrays.binarySearch(FUNCTIONS, string) != -1;
    }

    private static int getOperatorPriority(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
            case "%":
                return 2;
            case "^":
                return 3;
            case "acos":
            case "asin":
            case "atan":
            case "cos":
            case "ctg":
            case "ln":
            case "log":
            case "mod":
            case "sin":
            case "sqrt":
            case "tan":
                return 4;
            default:
                return -1;
        }
    }

    private static double executeOperator(Stack<Double> stack, String operator) {
        double secondOperand = stack.pop();
        double firstOperand = stack.pop();
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                return firstOperand / secondOperand;
            case "^":
                return Math.pow(firstOperand, secondOperand);
            case "%":
                return firstOperand % secondOperand;
        }
        return Double.POSITIVE_INFINITY;
    }

    private static double executeFunction(Stack<Double> stack, String function) {
        double operand = stack.pop();
        switch (function) {
            case "acos":
                return Math.acos(operand);
            case "asin":
                return Math.asin(operand);
            case "atan":
                return Math.atan(operand);
            case "cos":
                return Math.cos(operand);
            case "ctg":
                return 1 - Math.tan(operand);
            case "ln":
                return Math.log(operand);
            case "log":
                return Math.log10(operand);
            case "sin":
                return Math.sin(operand);
            case "sqrt":
                return Math.sqrt(operand);
            case "tan":
                return Math.tan(operand);
        }
        return Double.NaN;
    }
}
