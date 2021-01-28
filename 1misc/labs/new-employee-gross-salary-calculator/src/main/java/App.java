import learn.methods.Methods;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {

        Methods methods = new Methods();

        LocalDate startDate = LocalDate.of(2020,3,1);
        BigDecimal startingSalary = new BigDecimal("38000.00");

        BigDecimal salary = methods.calculateGrossSalary(startDate, startingSalary);

        System.out.println(salary);

    }

}
