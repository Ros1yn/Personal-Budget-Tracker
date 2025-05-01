package pl.ros1yn.personalbudgetapi.analytics.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.analytics.response.DailySpendingsResponse;
import pl.ros1yn.personalbudgetapi.analytics.response.ExpensesOfTheMonth;
import pl.ros1yn.personalbudgetapi.analytics.response.MaxAndMinSpendingInTheMonthResponse;
import pl.ros1yn.personalbudgetapi.analytics.response.TotalMonthlySpending;
import pl.ros1yn.personalbudgetapi.analytics.utils.MinMaxInTheMonthHelper;
import pl.ros1yn.personalbudgetapi.analytics.utils.MonthlyAveragePerCategoryHelper;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnalyticsService {

    private final ExpensesRepository expensesRepository;
    private MonthlyAveragePerCategoryHelper monthlyAveragePerCategoryHelper;

    private MinMaxInTheMonthHelper minMaxInTheMonthHelper;

    public ResponseEntity<Map<String, Map<String, String>>> getMonthlySpendingsAveragesPerCategory() {

        List<Expenses> expenses = expensesRepository.findAll();

        Map<String, Map<YearMonth, List<Double>>> expansesPerCategory =
                monthlyAveragePerCategoryHelper.groupExpensesPerCategory(expenses);

        Map<String, Map<String, String>> result =
                monthlyAveragePerCategoryHelper.formatAndExportCategoryAverages(expansesPerCategory);

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMinSpendingInTheMonth(YearMonth dateOfSpending) {

        double minSpendingAmount = minMaxInTheMonthHelper.getMinSpendingAmount(dateOfSpending);

        MaxAndMinSpendingInTheMonthResponse result = MaxAndMinSpendingInTheMonthResponse.builder()
                .spendingAmount("minSpendingAmount")
                .formattedAmount(String.format("%.2f", minSpendingAmount))
                .build();

        return ResponseEntity.ok(result);
    }



    public ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMaxSpendingInTheMonth(YearMonth dateOfSpending) {

        double minSpendingAmount = minMaxInTheMonthHelper.getMaxSpendingAmount(dateOfSpending);

        MaxAndMinSpendingInTheMonthResponse result = MaxAndMinSpendingInTheMonthResponse.builder()
                .spendingAmount("maxSpendingAmount")
                .formattedAmount(String.format("%.2f", minSpendingAmount))
                .build();

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<ExpensesOfTheMonth> getMonthlySpendingsInCategory(String categoryName, YearMonth dateOfSpending) {

        List<Expenses> expenses = expensesRepository.findAll().stream()
                .filter(exp -> exp.getCategory()
                        .getCategoryName().equalsIgnoreCase(categoryName))
                .filter(exp -> dateOfSpending.equals(YearMonth.from(exp.getExpenseDate())))
                .toList();

        Map<String, List<Double>> filteredExpenses = expenses.stream()
                .collect(Collectors.groupingBy(
                                exp -> YearMonth.from(dateOfSpending).toString(),
                                Collectors.mapping(Expenses::getAmount, Collectors.toList())
                        )
                );

        ExpensesOfTheMonth result = ExpensesOfTheMonth.builder()
                .category(categoryName)
                .expenses(filteredExpenses)
                .build();

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<TotalMonthlySpending> getTotalMonthlySpending(YearMonth dateOfSpending) {

        double totalMonthlySpendings = expensesRepository.findAll().stream()
                .filter(exp -> dateOfSpending.equals(YearMonth.from(exp.getExpenseDate())))
                .map(Expenses::getAmount)
                .mapToDouble(Double::doubleValue)
                .sum();

        TotalMonthlySpending result = TotalMonthlySpending.builder()
                .spendingsDate(dateOfSpending)
                .totalSpendings(String.format("%.2f", totalMonthlySpendings))
                .build();

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<DailySpendingsResponse>> getDailySpendings(LocalDate spendingaDate) {

        List<DailySpendingsResponse> dailySpendings = expensesRepository.findAll().stream()
                .filter(exp -> exp.getExpenseDate().isEqual(spendingaDate))
                .map(exp -> {

                    HashMap<String, String> dateAndSpendings = new HashMap<>();

                    dateAndSpendings.put(
                            exp.getExpenseDate().toString(),
                            String.format("%.2f", exp.getAmount())
                    );

                    return DailySpendingsResponse.builder()
                            .categoryName(exp.getCategory().getCategoryName())
                            .spendings(dateAndSpendings)
                            .build();
                }).toList();

        return ResponseEntity.ok(dailySpendings);
    }
}
