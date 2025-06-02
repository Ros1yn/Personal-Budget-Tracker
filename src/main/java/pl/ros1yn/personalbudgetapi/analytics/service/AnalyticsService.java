package pl.ros1yn.personalbudgetapi.analytics.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.analytics.response.*;
import pl.ros1yn.personalbudgetapi.analytics.utils.MinMaxInTheMonthHelper;
import pl.ros1yn.personalbudgetapi.analytics.utils.MonthlyAveragePerCategoryHelper;
import pl.ros1yn.personalbudgetapi.analytics.utils.SpendingTrendHelper;
import pl.ros1yn.personalbudgetapi.analytics.dto.TrendRequest;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class AnalyticsService {

    private final ExpensesRepository expensesRepository;
    private MonthlyAveragePerCategoryHelper monthlyAveragePerCategoryHelper;
    private SpendingTrendHelper spendingTrendHelper;
    private MinMaxInTheMonthHelper minMaxInTheMonthHelper;

    public ResponseEntity<Map<String, Map<String, String>>> getMonthlySpendingsAveragesPerCategory() {

        log.info("Fetching all expenses to calculate monthly averages per category");

        List<Expenses> expenses = expensesRepository.findAll();
        log.info("Retrieved {} expenses from repository", expenses.size());

        Map<String, Map<YearMonth, List<Double>>> expensesPerCategory =
                monthlyAveragePerCategoryHelper.groupExpensesPerCategory(expenses);
        log.info("Grouped expenses per category: {}", expensesPerCategory.keySet());

        Map<String, Map<String, String>> result =
                monthlyAveragePerCategoryHelper.formatAndExportCategoryAverages(expensesPerCategory);
        log.info("Formatted and exported category averages");

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMinSpendingInTheMonth(YearMonth dateOfSpending) {

        log.info("Getting minimum spending for month: {}", dateOfSpending);

        double minSpendingAmount = minMaxInTheMonthHelper.getMinSpendingAmount(dateOfSpending);
        log.info("Minimum spending amount for {} is {}", dateOfSpending, minSpendingAmount);

        MaxAndMinSpendingInTheMonthResponse result = MaxAndMinSpendingInTheMonthResponse.builder()
                .spendingAmount(String.format("%.2f", minSpendingAmount))
                .build();

        log.info("Built Min Spending response: {}", result);

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMaxSpendingInTheMonth(YearMonth dateOfSpending) {

        log.info("Getting maximum spending for month: {}", dateOfSpending);

        double maxSpendingAmount = minMaxInTheMonthHelper.getMaxSpendingAmount(dateOfSpending);
        log.info("Maximum spending amount for {} is {}", dateOfSpending, maxSpendingAmount);

        MaxAndMinSpendingInTheMonthResponse result = MaxAndMinSpendingInTheMonthResponse.builder()
                .spendingAmount(String.format("%.2f", maxSpendingAmount))
                .build();

        log.info("Built Max Spending response: {}", result);

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<ExpensesOfTheMonth> getMonthlySpendingsInCategory(String categoryName, YearMonth dateOfSpending) {

        log.info("Fetching monthly spendings for category: {} and month: {}", categoryName, dateOfSpending);

        List<Expenses> expenses = expensesRepository.findAll().stream()
                .filter(exp -> exp.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .filter(exp -> dateOfSpending.equals(YearMonth.from(exp.getExpenseDate())))
                .toList();

        log.info("Filtered {} expenses for category '{}' in month {}", expenses.size(), categoryName, dateOfSpending);

        Map<String, List<Double>> filteredExpenses = expenses.stream()
                .collect(Collectors.groupingBy(
                        exp -> YearMonth.from(dateOfSpending).toString(),
                        Collectors.mapping(Expenses::getAmount, Collectors.toList())
                ));

        log.info("Grouped expenses map: {}", filteredExpenses);

        ExpensesOfTheMonth result = ExpensesOfTheMonth.builder()
                .category(categoryName)
                .expenses(filteredExpenses)
                .build();

        log.info("Built ExpensesOfTheMonth response: {}", result);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<TotalMonthlySpending> getTotalMonthlySpending(YearMonth dateOfSpending) {

        log.info("Calculating total monthly spending for: {}", dateOfSpending);

        double totalMonthlySpendings = expensesRepository.findAll().stream()
                .filter(exp -> dateOfSpending.equals(YearMonth.from(exp.getExpenseDate())))
                .map(Expenses::getAmount)
                .mapToDouble(Double::doubleValue)
                .sum();

        log.info("Total spending amount for {}: {}", dateOfSpending, totalMonthlySpendings);

        TotalMonthlySpending result = TotalMonthlySpending.builder()
                .spendingsDate(dateOfSpending)
                .totalSpendings(String.format("%.2f", totalMonthlySpendings))
                .build();

        log.info("Built TotalMonthlySpending response: {}", result);

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

        log.info("Found {} daily spending entries for date {}", dailySpendings.size(), spendingaDate);

        return ResponseEntity.ok(dailySpendings);
    }


    public ResponseEntity<TrendResponse> getSpendingTrend(TrendRequest requestedParams) {

        log.info("Called getSpendingTrend for category: {}, date range: {} - {}",
                requestedParams.getCategoryName(),
                requestedParams.getDateFrom(),
                requestedParams.getDateTo()
        );

        Map<YearMonth, Double> spendingsDatesWithAmount = spendingTrendHelper.getSpendingsDatesWithAmounts(requestedParams);
        log.info("Retrieved {} spending entries", spendingsDatesWithAmount.size());

        List<Double> spendingsPerMonth = spendingTrendHelper.getSpendingsPerMonth(spendingsDatesWithAmount);

        SimpleRegression regression = spendingTrendHelper.buildRegression(spendingsPerMonth);

        double slope = regression.getSlope();
        log.info("Calculated regression slope: {}", slope);

        TrendResponse response = spendingTrendHelper.buildResponse(slope);

        if (response.getChangeRate().equalsIgnoreCase("NaN")){
            response.setChangeRate("0.0");
        }
        log.info("Built response: {}", response);

        return ResponseEntity.ok(response);
    }




}
