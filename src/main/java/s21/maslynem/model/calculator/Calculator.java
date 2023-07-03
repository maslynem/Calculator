package s21.maslynem.model.calculator;

import javafx.collections.ObservableList;
import s21.maslynem.model.exceptions.WrongExpressionException;
import s21.maslynem.model.utils.StringUtils;

import java.nio.file.Path;
import java.util.*;

public class Calculator {

    private final HistoryOfModel history;

    public Calculator() {
        history = new HistoryOfModel();
    }

    public Calculator(Path path) {
        this();
        loadHistory(path);
    }

    public double calculate(String expression, boolean saveHistory) {
        double result = calculate(expression);
        if (saveHistory) {
            history.addNewData(expression + "=" + result);
        }
        return result;
    }

    public void loadHistory(Path path) {
        history.tryToLoadDataFromFile(path);
    }

    public void saveHistory(Path path) {
        history.saveDataToFile(path);
    }

    public void clearHistory() {
        history.clearHistory();
    }

    public ObservableList<String> getHistoryContent() {
        return history.getHistory();
    }

    private double calculate(String expression) {
        List<String> postfix = FromInfixToPostfixTransformer.transform(expression);
        return calculateByPostfix(postfix);
    }

    private double calculateByPostfix(List<String> postfix) {
        try {
            Deque<Double> stack = new ArrayDeque<>();
            for (String token : postfix) {
                if (StringUtils.isOperand(token)) {
                    stack.push(Double.parseDouble(token));
                } else if (StringUtils.isOperator(token)) {
                    stack.push(executeOperator(stack, token));
                } else if (StringUtils.isFunction(token)){
                    stack.push(executeFunction(stack, token));
                } else {
                    throw new WrongExpressionException("Wrong expression: undefined token [" + token +"]");
                }
            }
            double result = stack.pop();
            if (!stack.isEmpty()) {
                throw new WrongExpressionException("Wrong expression: missing operator");
            }
            return result;
        } catch (NoSuchElementException exception) {
            throw new WrongExpressionException("Wrong expression: unexpected operator");
        }
    }

    private  double executeOperator(Deque<Double> stack, String operator) {
        if (operator.equals("~")) {
            return 0 - stack.pop();
        } else {
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
    }

    private  double executeFunction(Deque<Double> stack, String function) {
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
