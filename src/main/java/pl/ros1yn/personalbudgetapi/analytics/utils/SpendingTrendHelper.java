package pl.ros1yn.personalbudgetapi.analytics.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.analytics.dto.TrendRequest;
import pl.ros1yn.personalbudgetapi.analytics.response.TrendResponse;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
@Slf4j
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

    public Map<YearMonth, Double> getSpendingsDatesWithAmounts(TrendRequest requestedParams) {
        YearMonth dateFrom = requestedParams.getDateFrom();
        YearMonth dateTo   = requestedParams.getDateTo();
        String requestedCategory = requestedParams.getCategoryName();

        if (dateFrom.isAfter(dateTo)) {
            YearMonth tmp = dateFrom;
            dateFrom = dateTo;
            dateTo = tmp;
        }

        YearMonth finalDateFrom = dateFrom;
        YearMonth finalDateTo = dateTo;


        return expensesRepository.findAll().stream()
                .filter(exp -> {
                    YearMonth expenseMonth = YearMonth.from(exp.getExpenseDate());
                    return !expenseMonth.isBefore(finalDateFrom) && !expenseMonth.isAfter(finalDateTo);
                })
                .filter(exp -> exp.getCategory().getCategoryName()
                        .equalsIgnoreCase(requestedCategory))
                .sorted(Comparator.comparing(Expenses::getExpenseDate))
                .collect(Collectors.groupingBy(
                        exp -> YearMonth.from(exp.getExpenseDate()),
                        Collectors.summingDouble(Expenses::getAmount)
                ));
    }


    public List<Double> getSpendingsPerMonth(Map<YearMonth, Double> spendingsDatesWithAmount) {
        ArrayList<Double> spendingsPerMonth = new ArrayList<>();
        spendingsDatesWithAmount.forEach((date, amount) -> spendingsPerMonth.add(amount));
        log.info("Collected spending amounts per month: {}", spendingsPerMonth);
        return spendingsPerMonth;
    }

    public SimpleRegression buildRegression(List<Double> spendingsPerMonth) {
        SimpleRegression regression = new SimpleRegression();
        IntStream.range(0, spendingsPerMonth.size())
                .forEach(i -> regression.addData(i, spendingsPerMonth.get(i)));
        return regression;
    }

}
