package pl.ros1yn.personalbudgetapi.analytics.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.YearMonth;

@Component
@AllArgsConstructor
public class MinMaxInTheMonthHelper {

    private final ExpensesRepository expensesRepository;

    public double getMinSpendingAmount(YearMonth dateOfSpending) {
        return expensesRepository.findAll().stream()
                .filter(exp -> YearMonth.from(exp.getExpenseDate()).equals(dateOfSpending))
                .map(Expenses::getAmount)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0.00);
    }

    public double getMaxSpendingAmount(YearMonth dateOfSpending) {
        return expensesRepository.findAll().stream()
                .filter(exp -> YearMonth.from(exp.getExpenseDate()).equals(dateOfSpending))
                .map(Expenses::getAmount)
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.00);
    }


}
