package s21.maslynem.model.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import s21.maslynem.model.exceptions.WrongExpressionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private final double DELTA = 1e-7;

    @Test
    void Calculator_SaveHistory_ShouldCreateNotEmptyTempFile() throws IOException {
        Path tempFile = Files.createTempFile("temp", ".txt");
        String expression = "1.54837495+1.34521343";
        calculator.calculate(expression, true);
        calculator.saveHistory(tempFile);
        Assertions.assertTrue(Files.exists(tempFile) && Files.size(tempFile) != 0);



        Calculator newCalculator = new Calculator(tempFile);
        Assertions.assertIterableEquals(calculator.getHistoryContent(), newCalculator.getHistoryContent());
    }

    @Test
    void Calculator_LoadHistory_Should_ReadDataFromFile_When_() throws IOException {
        Path tempFile = Files.createTempFile("temp", ".txt");
        String expression = "1.54837495+1.34521343";
        calculator.calculate(expression, true);
        calculator.saveHistory(tempFile);
        Calculator newCalculator = new Calculator(tempFile);
        Assertions.assertIterableEquals(calculator.getHistoryContent(), newCalculator.getHistoryContent());
    }

    @Test
    void Calculator_ClearHistory_ShouldDelAllDataFromHistory() {
        String expression = "1.54837495+1.34521343";
        calculator.calculate(expression, true);
        calculator.clearHistory();
        Assertions.assertTrue(calculator.getHistoryContent().isEmpty());
    }

    @Test
    void Calculator_Add_ShouldReturnCorrectResult() {
        String expression = "1.54837495+1.34521343";
        double result = 1.54837495 + 1.34521343;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_Multiply_ShouldReturnCorrectResult() {
        String expression = "1+1/3*2^12-44";
        double result = 1322.3333333;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_sin_ShouldReturnCorrectResult() {
        String expression = "sin(90)";
        double result = Math.sin(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_cos_ShouldReturnCorrectResult() {
        String expression = "cos(90)";
        double result = Math.cos(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_tan_ShouldReturnCorrectResult() {
        String expression = "tan(90)";
        double result = Math.tan(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_sqrt_ShouldReturnCorrectResult() {
        String expression = "sqrt(90)";
        double result = Math.sqrt(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_ln_ShouldReturnCorrectResult() {
        String expression = "ln(90)";
        double result = Math.log(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_log_ShouldReturnCorrectResult() {
        String expression = "log(90)";
        double result = Math.log10(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_acos_ShouldReturnCorrectResult() {
        String expression = "acos(0.5)";
        double result = Math.acos(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_asin_ShouldReturnCorrectResult() {
        String expression = "asin(0.5)";
        double result = Math.asin(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_atan_ShouldReturnCorrectResult() {
        String expression = "atan(0.5)";
        double result = Math.atan(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_mod_ShouldReturnCorrectResult() {
        String expression = "3.4 % 3";
        double result = 3.4 % 3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_UnaryOperatorAddPositiveNumber_ShouldReturnCorrectResult() {
        String expression = "-1 + 2";
        double result = 1;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_UnaryOperatorAddNegativeNumber_ShouldReturnCorrectResult() {
        String expression = "-1 + -2";
        double result = -3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_UnaryOperatorWithFunction_ShouldReturnCorrectResult() {
        String expression = "-sin(90)";
        double result = -Math.sin(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_UnaryOperatorWithFunctionMultiplyFunction_ShouldReturnCorrectResult() {
        String expression = "-sin(90) * -sin(90)";
        double result = 0.79923003452;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_DotSumAndDiv_ShouldReturnCorrectResult() {
        String expression = ".3 + .3/.3";
        double result = 1.3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_DotSumAndMulti_ShouldReturnCorrectResult() {
        String expression = ".3 + .3*.3";
        double result = 0.39;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_DotSumAndMultiWithBrackets_ShouldReturnCorrectResult() {
        String expression = ".3 + .3*(.3)";
        double result = 0.39;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_DotWithBracketsAndMod_ShouldReturnCorrectResult() {
        String expression = "(.3 + .3) % .3";
        double result = 0;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_RightAssociativeOp_ShouldReturnCorrectResult() {
        String expression = "2^3^2";
        double result = 512;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_RightAssociativeOpWithBrackets_ShouldReturnCorrectResult() {
        String expression = "2^(3+3)^2";
        double result = 68719476736d;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_HardCalculationTestOne_ShouldReturnCorrectResult() {
        String expression = "(1+2)*4*cos(3.141592*7-2)+sin(2*3.141592)";
        double result = 4.9938107;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_HardCalculationTestTwo_ShouldReturnCorrectResult() {
        String expression = "-sqrt(3.141592^log(5-3.141592))+log(55/(2+3.141592))";
        double result = -0.1372805;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_DivByZeroTest_ShouldReturnCorrectResult() {
        String expression = "1/(-3-4+7)";
        double result = Double.POSITIVE_INFINITY;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_ModByZeroTest_ShouldReturnCorrectResult() {
        String expression = "3 % (3-3)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_PowNegativeInBrackets_ShouldReturnCorrectResult() {
        String expression = "(-2)^2";
        double result = 4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_PowNegative_ShouldReturnCorrectResult() {
        String expression = "-2^2";
        double result = -4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_LotOfSin_ShouldReturnCorrectResult() {
        String expression = "sin(sin(sin(sin(sin(90)))))";
        double result = 0.60239260455;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_LogWithHighDegree_ShouldReturnCorrectResult() {
        String expression = "log((9.980000)^123)";
        double result = 122.893056578;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_BracketsWithNumber_ShouldReturnCorrectResult() {
        String expression = "(123)";
        double result = 123;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_BracketsWithNumberMultiply_ShouldReturnCorrectResult() {
        String expression = "(123) * (123)";
        double result = 15129;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }


    @Test
    void Calculator_ScientificNotationLowCase_ShouldReturnCorrectResult() {
        String expression = "1 * 1e+2";
        double result = 1 * 1e+2;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void Calculator_ScientificNotationUpCase_ShouldReturnCorrectResult() {
        String expression = "1E+2 + 2E-4";
        double result = 1E+2 + 2E-4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }


    @ParameterizedTest
    @ValueSource(strings = {"1 + sqrt(-1)", "sqrt(3-4)", "ln(3-4)", "log(3-4)", "asin(-2)", "asin(2)", "acos(-2)", "acos(2)"})
    void Calculator_IncorrectExpressions_ShouldReturnNaN(String expression) {
        Assertions.assertEquals(Double.NaN, calculator.calculate(expression, false), DELTA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1&3", "1+", "1*", "*1", "1(", "1)", "asin", "()", "1.3.4", "tan()+3", "sqrt3", "-*3", "% 3", "90-3*()*3", "123-34-", "sin(3+4-)", "sssin(2)+1", "cooos(2)+2", "aacos(0.3)"})
    void Calculate_WrongInput_WrongExpressionException(String expression) {
        Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression, false));
    }


}