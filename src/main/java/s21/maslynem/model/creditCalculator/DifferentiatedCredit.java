package s21.maslynem.model.creditCalculator;

import java.util.List;

public class DifferentiatedCredit {
    private double allSum = 0;
    private double debt = 0;
    private List<Double> monthPay;

    public DifferentiatedCredit(double allSum, double debt, List<Double> monthPay) {
        this.allSum = allSum;
        this.debt = debt;
        this.monthPay = monthPay;
    }

    public double getAllSum() {
        return allSum;
    }

    public double getDebt() {
        return debt;
    }

    public List<Double> getMonthPay() {
        return monthPay;
    }
}
