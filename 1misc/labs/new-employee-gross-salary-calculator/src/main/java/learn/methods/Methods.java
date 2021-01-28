package learn.methods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

public class Methods {

    public BigDecimal calculateGrossSalary(LocalDate dateStart, BigDecimal salary) {
        LocalDate payDate = LocalDate.of(dateStart.getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(dateStart.plusYears(1).getYear(), 1, 1);

        BigDecimal totalSalaryMade = new BigDecimal("0.00");
        BigDecimal monthlySalary = salary.divide(new BigDecimal("12"), 2, RoundingMode.HALF_EVEN);

        if (dateStart.getDayOfMonth() != 1) {
            throw new IllegalArgumentException();
        }

        for (; payDate.compareTo(endDate) < 0; payDate = payDate.plusMonths(1)) {
            if (!payDate.isBefore(dateStart)) {
                totalSalaryMade = totalSalaryMade.add(monthlySalary);
                salary = salary.add(calculateRaise(dateStart, payDate, salary));
                monthlySalary = salary.divide(new BigDecimal("12"), 2, RoundingMode.HALF_EVEN);
            }
        }

        return totalSalaryMade;
    }

    private BigDecimal calculateRaise(LocalDate startDate, LocalDate payDate, BigDecimal salary) {
        int monthsWorked = Period.between(startDate,payDate).getMonths();

        if (monthsWorked == 3) {
            return salary.multiply(new BigDecimal("0.03"));
        }

        if (Period.between(startDate,payDate).getMonths() == 6) {
            return salary.multiply(new BigDecimal("0.06"));
        }

        return new BigDecimal("0.00");
    }
}