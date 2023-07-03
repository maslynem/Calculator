package s21.maslynem.model;

import java.util.*;

class FromInfixToPostfixTransformer {

    private FromInfixToPostfixTransformer() {
    }

    static List<String> transform(String expression) {
        String newExpression = prepareExpression(expression);
        return castToPostfix(newExpression);
    }

    private static String prepareExpression(String expression) {
        String newExpression = expression
                .toLowerCase()
                .replace(" ", "")
                .replace("π", String.valueOf(Math.PI))
                .replace("√", "sqrt")
                .replace(",", ".")
                .replace("(-", "(~")
                .replace("+-", "+~")
                .replace("*-", "*~")
                .replace("%-", "%~")
                .replace("/-", "/~")
                .replace("(+", "(")
                .replace("-+", "-")
                .replace("~+", "-")
                .replace("*+", "*")
                .replace("%+", "%")
                .replace("/+", "/");
        char ch = newExpression.charAt(0);
        if (ch == '-' || ch == '+') {
            newExpression = "0" + newExpression;
        }
        return newExpression;
    }

    private static List<String> castToPostfix(String expression) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokens = new StringTokenizer(expression, "%*+-~/^()e", true);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token.equals("e")) {
                handleExpCase(token, tokens, postfix);
            } else if (StringUtils.isOperand(token)) {
                postfix.add(token);
            } else if (StringUtils.isFunction(token)) {
                stack.push(token);
            } else if (StringUtils.isOperator(token)) {
                handleOperatorCase(token, stack, postfix);
            } else if (StringUtils.isOpenBracket(token)) {
                stack.push(token);
            } else if (StringUtils.isCloseBracket(token)) {
                handleCloseBracketCase(stack, postfix);
            } else {
                throw new WrongExpressionException(String.format("[%s] is not available.", token));
            }
        }
        while (!stack.isEmpty()) {
            String token = stack.pop();
            if (StringUtils.isOpenBracket(token)) {
                throw new WrongExpressionException("Wrong count of brackets.");
            }
            postfix.add(token);
        }
        return postfix;
    }

    private static void handleExpCase(String token, StringTokenizer tokens, List<String> postfix) {
        String operand = postfix.get(postfix.size() - 1);
        postfix.remove(postfix.size() - 1);
        postfix.add(operand + token + tokens.nextToken() + tokens.nextToken());
    }

    private static void handleOperatorCase(String token, Deque<String> stack, List<String> postfix) {
        while (!stack.isEmpty() && StringUtils.isOperator(stack.peek()) &&
                (getOperatorPriority(stack.peek()) > getOperatorPriority(token) ||
                        getOperatorPriority(stack.peek()) == getOperatorPriority(token) && !token.equals("^"))) {
            postfix.add(stack.pop());
        }
        stack.push(token);
    }

    private static void handleCloseBracketCase(Deque<String> stack, List<String> postfix) {
        try {
            while (!StringUtils.isOpenBracket(stack.peek())) {
                postfix.add(stack.pop());
            }
            stack.pop();
            if (!stack.isEmpty() && StringUtils.isFunction(stack.peek())) {
                postfix.add(stack.pop());
            }
        } catch (NoSuchElementException exception) {
            throw new WrongExpressionException("Wrong count of brackets.");
        }
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
            case "~":
                return 5;
            default:
                return -1;
        }
    }
}
