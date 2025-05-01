package pl.ros1yn.personalbudgetapi.analytics.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MonthlyAveragePerCategoryHelper {

    public Map<String, Map<YearMonth, List<Double>>> groupExpensesPerCategory(List<Expenses> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        exp -> exp.getCategory().getCategoryName(),
                        Collectors.groupingBy(
                                exp -> YearMonth.from(exp.getExpenseDate()),
                                Collectors.mapping(Expenses::getAmount, Collectors.toList())
                        )
                ));
    }

    public Map<String, Map<String, String>> formatAndExportCategoryAverages(
            Map<String, Map<YearMonth, List<Double>>> expansesPerCategory) {

        Map<String, Map<String, String>> result = new HashMap<>();

        expansesPerCategory.forEach((categoryName, monthlyData) -> {
            Map<String, String> monthAverageMap = calculateAverages(monthlyData);
            result.put(categoryName, monthAverageMap);
        });

        return result;
    }


    private Map<String, String> calculateAverages(Map<YearMonth, List<Double>> monthlyData) {

        Map<String, String> monthlyAverages = new HashMap<>();

        monthlyData.forEach((yearMonth, amounts) -> {
            double avg = amounts.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            String formattedMonth = yearMonth.toString();

            monthlyAverages.put(formattedMonth, String.format("%.2f", avg));
        });

        return monthlyAverages;
    }

}
