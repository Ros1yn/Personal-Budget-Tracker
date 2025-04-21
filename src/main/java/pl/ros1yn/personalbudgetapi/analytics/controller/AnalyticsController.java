package pl.ros1yn.personalbudgetapi.analytics.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ros1yn.personalbudgetapi.analytics.service.AnalyticsService;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
@Slf4j
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/average")
    public ResponseEntity<List<Map<String, Map<String, String>>>> getMonthlySpendingsAverages(){
        return analyticsService.getMonthlySpendingsAveragesPerCategory();
    }

    @GetMapping("/minMonthlySpending/{dateOfSpending}")
    public ResponseEntity<Map<String, String>> getMinSpendingInTheMonth(@PathVariable @DateTimeFormat(pattern = "yyyy-MM")YearMonth dateOfSpending){
        return analyticsService.getMinSpendingInTheMonth(dateOfSpending);
    }

}
