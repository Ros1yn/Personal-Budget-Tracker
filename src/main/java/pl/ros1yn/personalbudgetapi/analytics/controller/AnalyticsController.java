package pl.ros1yn.personalbudgetapi.analytics.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ros1yn.personalbudgetapi.analytics.response.*;
import pl.ros1yn.personalbudgetapi.analytics.service.AnalyticsService;
import pl.ros1yn.personalbudgetapi.analytics.dto.TrendRequest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
@Slf4j
class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/average/")
     ResponseEntity<Map<String, Map<String, String>>> getMonthlySpendingsAverages() {

        log.info("Recived request for getMonthlySpendingsAverages.");
        return analyticsService.getMonthlySpendingsAveragesPerCategory();
    }

    @GetMapping("/minMonthlySpending/{dateOfSpending}")
    ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMinSpendingInTheMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM")YearMonth dateOfSpending){

        log.info("Recived request for getMinSpendingInTheMonth with date: {}.", dateOfSpending);
        return analyticsService.getMinSpendingInTheMonth(dateOfSpending);
    }

    @GetMapping("/maxMonthlySpending/{dateOfSpending}")
    ResponseEntity<MaxAndMinSpendingInTheMonthResponse> getMaxSpendingInTheMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth dateOfSpending) {

        log.info("Recived request for getMaxSpendingInTheMonth with date: {}.", dateOfSpending);
        return analyticsService.getMaxSpendingInTheMonth(dateOfSpending);
    }

    @GetMapping("/expensesOfTheMonth/{categoryName}/{dateOfSpending}")
     ResponseEntity<ExpensesOfTheMonth> getExpensesOfTheMonth(
            @PathVariable String categoryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth dateOfSpending){

        log.info("Recived request for getMaxSpendingInTheMonth with category name: {} | and date: {}", categoryName, dateOfSpending);
        return analyticsService.getMonthlySpendingsInCategory(categoryName, dateOfSpending);
    }

    @GetMapping("/totalMonthlySpending/{dateOfSpending}")
     ResponseEntity<TotalMonthlySpending> getTotalMonthlySpending(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth dateOfSpending) {

        log.info("Recived request for getTotalMonthlySpending with date: {}.", dateOfSpending);
        return analyticsService.getTotalMonthlySpending(dateOfSpending);
    }

    @GetMapping("/dailyspendings/{spendingaDate}")
    ResponseEntity<List<DailySpendingsResponse>> getDailySpendings(@PathVariable LocalDate spendingaDate){

        log.info("Recived request for getDailySpendings with date: {}.", spendingaDate);
        return analyticsService.getDailySpendings(spendingaDate);
    }

    @GetMapping("/spendingTrend/")
    ResponseEntity<TrendResponse> getSpendingTrend(@RequestBody TrendRequest requestParams) {

        log.info("Recived request for getSpendingTrend with body: {}, {}, {}.", requestParams.getCategoryName(), requestParams.getDateFrom(), requestParams.getDateTo());
        return analyticsService.getSpendingTrend(requestParams);
    }

}
