package s21.maslynem.model.creditCalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CreditCalculatorTest {
    private final CreditCalculator calculator = new CreditCalculator();

    @Test
    void CountAnnuityCredit_CorrectArguments_ShouldReturnCorrectResult() {
        AnnuityCredit annuityCredit = calculator.countAnnuityCredit(50000, 8.5, 45);
        Assertions.assertEquals(1301.48, annuityCredit.getMonthPay(), 0.01);
        Assertions.assertEquals(8566.6, annuityCredit.getDebt(), 0.01);
        Assertions.assertEquals(58566.6, annuityCredit.getAllSum(), 0.01);
    }

    @Test
    void CountAnnuityCredit_NegativeSum_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countAnnuityCredit(-50000, 8.5, 45));
    }

    @Test
    void CountAnnuityCredit_NegativeRate_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countAnnuityCredit(50000, -8.5, 45));
    }

    @Test
    void CountAnnuityCredit_NegativeDate_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countAnnuityCredit(50000, 8.5, -45));
    }

    @Test
    void CountDifferentiatedCredit_CorrectArguments_ShouldReturnCorrectResult() {
        DifferentiatedCredit differentiatedCredit = calculator.countDifferentiatedCredit(50000, 8.5, 10);
       double[] expectedMonthPayments = new double[]{5354.17, 5318.75, 5283.33, 5247.92, 5212.5, 5177.08, 5141.67, 5106.25, 5070.83, 5035.42};
        Assertions.assertEquals(1947.92, differentiatedCredit.getDebt(), 0.01);
        Assertions.assertEquals(51947.92, differentiatedCredit.getAllSum(), 0.01);
        Assertions.assertArrayEquals(expectedMonthPayments, toArray(differentiatedCredit.getMonthPay()), 0.01);
    }

    @Test
    void CountDifferentiatedCredit_NegativeSum_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countDifferentiatedCredit(-50000, 8.5, 45));
    }

    @Test
    void CountDifferentiatedCredit_NegativeRate_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countDifferentiatedCredit(50000, -8.5, 45));
    }

    @Test
    void CountDifferentiatedCredit_NegativeDate_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.countDifferentiatedCredit(50000, 8.5, -45));
    }

    private double[] toArray(List<Double> list) {
        return list.stream().mapToDouble(Number::doubleValue).toArray();
    }
}
