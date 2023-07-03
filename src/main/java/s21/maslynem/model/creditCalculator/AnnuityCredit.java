package s21.maslynem.model.creditCalculator;

public class AnnuityCredit {
    private double allSum;
    private double debt;
    private double monthPay;

    AnnuityCredit(double sum, double debt, double monthPay) {
        this.allSum = sum;
        this.debt = debt;
        this.monthPay = monthPay;
    }

    public double getAllSum() {
        return allSum;
    }

    public double getDebt() {
        return debt;
    }

    public double getMonthPay() {
        return monthPay;
    }
}
