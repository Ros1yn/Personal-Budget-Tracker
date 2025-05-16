package pl.ros1yn.personalbudgetapi.analytics.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.analytics.response.TrendResponse;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SpendingTrendHelper {

    private final ExpensesRepository expensesRepository;

    public TrendResponse buildResponse(double slope) {
        return TrendResponse.builder()
                .trend(getTrendDirection(slope))
                .changeRate(String.format("%.2f", slope))
                .build();
    }

    private String getTrendDirection(double slope) {
        String trend;
        if (slope > 5) {
            trend = "growing";
        } else if (slope < -5) {
            trend = "declining";
        } else {
            trend = "stable";
        }
        return trend;
    }

    //TODO single month predicate
    public Map<YearMonth, Double> getSpendingsDatesWithAmounts(String categoryName, YearMonth dateFrom, YearMonth dateTo) {
        return expensesRepository.findAll().stream()
                .filter(exp -> YearMonth.from(exp.getExpenseDate()).plusMonths(1).isAfter(dateFrom))
                .filter(exp -> YearMonth.from(exp.getExpenseDate()).minusMonths(1).isBefore(dateTo))
                .filter(exp -> exp.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .sorted(Comparator.comparing(Expenses::getExpenseDate))
                .collect(Collectors.groupingBy(exp ->
                                YearMonth.from(exp.getExpenseDate()),
                        Collectors.summingDouble(Expenses::getAmount)
                ));
    }

}
