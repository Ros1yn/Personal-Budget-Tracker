package pl.ros1yn.personalbudgetapi.analytics.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ros1yn.personalbudgetapi.analytics.response.ExpensesOfTheMonth;
import pl.ros1yn.personalbudgetapi.analytics.service.AnalyticsService;

import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
@Slf4j
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/average")
    public ResponseEntity<Map<String, Map<String, String>>> getMonthlySpendingsAverages() {

        log.info("Recived request for getMonthlySpendingsAverages.");
        return analyticsService.getMonthlySpendingsAveragesPerCategory();
    }

    @GetMapping("/minMonthlySpending/{dateOfSpending}")
    public ResponseEntity<Map<String, String>> getMinSpendingInTheMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM")YearMonth dateOfSpending){

        log.info("Recived request for getMinSpendingInTheMonth with date: {}.", dateOfSpending);
        return analyticsService.getMinSpendingInTheMonth(dateOfSpending);
    }

    @GetMapping("/maxMonthlySpending/{dateOfSpending}")
    public ResponseEntity<Map<String, String>> getMaxSpendingInTheMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth dateOfSpending) {

        log.info("Recived request for getMaxSpendingInTheMonth with date: {}.", dateOfSpending);
        return analyticsService.getMaxSpendingInTheMonth(dateOfSpending);
    }

    //TODO zamiana z mapy na obiekt
    @GetMapping("/expensesOfTheMonth/{categoryName}/{dateOfSpending}")
    public ResponseEntity<ExpensesOfTheMonth> getExpensesOfTheMonth(
            @PathVariable String categoryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth dateOfSpending){

        log.info("Recived request for getMaxSpendingInTheMonth with category name: {} | and date: {}", categoryName, dateOfSpending);
        return analyticsService.getMonthlySpendingsInCategory(categoryName, dateOfSpending);
    }
}
