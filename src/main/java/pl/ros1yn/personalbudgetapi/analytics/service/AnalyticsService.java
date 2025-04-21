package pl.ros1yn.personalbudgetapi.analytics.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.analytics.utils.MonthlyAveragePerCategoryHelper;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AnalyticsService {

    private final ExpensesRepository expensesRepository;
    private MonthlyAveragePerCategoryHelper monthlyAveragePerCategoryHelper;

    public ResponseEntity<List<Map<String, Map<String, String>>>> getMonthlySpendingsAveragesPerCategory() {

        List<Expenses> expenses = expensesRepository.findAll();

        Map<String, Map<YearMonth, List<Double>>> expansesPerCategory = monthlyAveragePerCategoryHelper.groupExpensesPerCategory(expenses);
        List<Map<String, Map<String, String>>> result = monthlyAveragePerCategoryHelper.formatAndExportCategoryAverages(expansesPerCategory);

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<Map<String, String>> getMinSpendingInTheMonth(YearMonth dateOfSpending) {

        double minSpendingAmount = expensesRepository.findAll().stream()
                .filter(exp -> YearMonth.from(exp.getExpenseDate()).equals(dateOfSpending))
                .map(Expenses::getAmount)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0.00);

        HashMap<String, String> result = new HashMap<>();
        result.put("minSpendingAmount", String.format("%.2f", minSpendingAmount));

        return ResponseEntity.ok(result);
    }
}
