package s21.maslynem.model.creditCalculator;

import java.util.ArrayList;
import java.util.List;

public class CreditCalculator {

    public AnnuityCredit countAnnuityCredit(double sum, double rate, double date) {
        if (sum <= 0 || rate <= 0 || date <= 0) {
            throw new IllegalArgumentException();
        }
        double i = rate / 1200;
        double monthPay = sum * ((i * Math.pow((1 + i), date)) / (Math.pow((1 + i), date) - 1));
        monthPay = Math.round(monthPay * 100) / 100.;
        double debt = monthPay * date - sum;
        double allSum = monthPay * date;
        return new AnnuityCredit(allSum, debt, monthPay);
    }

    public DifferentiatedCredit countDifferentiatedCredit(double sum, double rate, double date) {
        if (sum <= 0 || rate <= 0 || date <= 0) {
            throw new IllegalArgumentException();
        }
        double allSum = 0;
        double debt = 0;
        List<Double> monthPay = new ArrayList<>();
        for (int i = 1; i <= date; i++) {
            double temp = (sum - (sum / date) * (i - 1));
            double tempMonthPay = sum / date + temp * (rate / 1200);
            monthPay.add(tempMonthPay);
            allSum += tempMonthPay;
            if (i == date) {
                debt = allSum - sum;
            }
        }
        return new DifferentiatedCredit(allSum, debt, monthPay);
    }
}

