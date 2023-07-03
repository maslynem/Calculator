package s21.maslynem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    private Calculator calculator = new Calculator();
    private final double DELTA = 1e-7;

    @Test
    void sumTest() {
        String expression = "1.54837495+1.34521343";
        double result = 1.54837495 + 1.34521343;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void multiTest() {
        String expression = "1+1/3*2^12-44";
        double result = 1322.3333333;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void sinTest() {
        String expression = "sin(90)";
        double result = Math.sin(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void cosTest() {
        String expression = "cos(90)";
        double result = Math.cos(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void tanTest() {
        String expression = "tan(90)";
        double result = Math.tan(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void sqrtTest() {
        String expression = "sqrt(90)";
        double result = Math.sqrt(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void lnTest() {
        String expression = "ln(90)";
        double result = Math.log(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void logTest() {
        String expression = "log(90)";
        double result = Math.log10(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void acosTest() {
        String expression = "acos(0.5)";
        double result = Math.acos(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void asinTest() {
        String expression = "asin(0.5)";
        double result = Math.asin(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void atanTest() {
        String expression = "atan(0.5)";
        double result = Math.atan(0.5);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void modTest() {
        String expression = "3.4 % 3";
        double result = 3.4 % 3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void unaryOperatorSumPositiveNumberTest() {
        String expression = "-1 + 2";
        double result = 1;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void unaryOperatorSumNegativeNumberTest() {
        String expression = "-1 + -2";
        double result = -3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void unaryOperatorWithFunctionTest() {
        String expression = "-sin(90)";
        double result = -Math.sin(90);
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void unaryOperatorWithFunctionMultiplyFunctionTest() {
        String expression = "-sin(90) * -sin(90)";
        double result = 0.79923003452;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void dotSumAndDivTest() {
        String expression = ".3 + .3/.3";
        double result = 1.3;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void dotSumAndMultiTest() {
        String expression = ".3 + .3*.3";
        double result = 0.39;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void dotSumAndMultiWithBracketsTest() {
        String expression = ".3 + .3*(.3)";
        double result = 0.39;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void dotWithBracketsAndModTest() {
        String expression = "(.3 + .3) % .3";
        double result = 0;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void rightAssociativeOpTest() {
        String expression = "2^3^2";
        double result = 512;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void rightAssociativeOpWithBracketsTest() {
        String expression = "2^(3+3)^2";
        double result = 68719476736d;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void hardCalculationTestOne() {
        String expression = "(1+2)*4*cos(3.141592*7-2)+sin(2*3.141592)";
        double result = 4.9938107;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void hardCalculationTestTwo() {
        String expression = "-sqrt(3.141592^log(5-3.141592))+log(55/(2+3.141592))";
        double result = -0.1372805;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void DivByZeroTest() {
        String expression = "1/(-3-4+7)";
        double result = Double.POSITIVE_INFINITY;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void modByZeroTest() {
        String expression = "3 % (3-3)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void powNegativeInBracketsTest() {
        String expression = "(-2)^2";
        double result = 4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void powNegativeTest() {
        String expression = "-2^2";
        double result = -4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void lotOfSinTest() {
        String expression = "sin(sin(sin(sin(sin(90)))))";
        double result = 0.60239260455;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void logWithHighDegreeTest() {
        String expression = "log((9.980000)^123)";
        double result = 122.893056578;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void BracketsWithNumberTest() {
        String expression = "(123)";
        double result = 123;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void BracketsWithNumberMultiplyTest() {
        String expression = "(123) * (123)";
        double result = 15129;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }


    @Test
    void sqrtWithNegativeNumberTestOne() {
        String expression = "1 + sqrt(-1)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void sqrtWithNegativeNumberTestTwo() {
        String expression = "sqrt(3-4)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void lnWithNegativeNumberTest() {
        String expression = "ln(3-4)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void logWithNegativeNumberTest() {
        String expression = "log(3-4)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void asinWithNegativeNumberOutOfRangeTest() {
        String expression = "asin(-2)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void asinWithNumberOutOfRangeTest() {
        String expression = "asin(2)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }
    @Test
    void acosWithNegativeNumberOutOfRangeTest() {
        String expression = "acos(-2)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);    }
    @Test
    void acosWithNumberOutOfRangeTest() {
        String expression = "acos(2)";
        double result = Double.NaN;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);    }

    @Test
    void MulExpTestOne() {
        String expression = "1 * 1e+2";
        double result = 1 * 1e+2;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void MulExpTestTwo() {
        String expression = "1E+2 + 2E-4";
        double result = 1E+2 + 2E-4;
        Assertions.assertEquals(result, calculator.calculate(expression, false), DELTA);
    }

    @Test
    void WrongInputNumberAndSumTest() {
        String expression = "1+";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputNumberAndMultiplyTest() {
        String expression = "1*";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputMultiplyAndNumberTest() {
        String expression = "*1";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputLackOfCloseBracketTest() {
        String expression = "1(";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputLackOfOpenBracketTest() {
        String expression = "1)";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputAsinTest() {
        String expression = "asin";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputEmptyBracketsTest() {
        String expression = "()";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputLotOfDotsTest() {
        String expression = "1.3.4";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputEmptyBracketsInFunctionTest() {
        String expression = "tan()+3";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputFunctionWithoutBracketsTest() {
        String expression = "sqrt3";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputSubAndMulTest() {
        String expression = "-*3";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputModWithOneNumberTest() {
        String expression = "% 3";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputExpressionWithEmptyBracketsTest() {
        String expression = "90-3*()*3";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }

    @Test
    void WrongInputLackOfOperandTest() {
        String expression = "123-34-";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputLackOfOperandInFunctionTest() {
        String expression = "sin(3+4-)";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputSinNameTest() {
        String expression = "sssin(2)+1";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputCosNameTest() {
        String expression = "cooos(2)+2";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }
    @Test
    void WrongInputAcosNameTest() {
        String expression = "aacos(0.3)";
        Assertions.assertThrows(WrongExpressionException.class,() -> calculator.calculate(expression, false));
    }




}