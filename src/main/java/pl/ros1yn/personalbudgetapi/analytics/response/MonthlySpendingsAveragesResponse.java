package pl.ros1yn.personalbudgetapi.analytics.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MonthlySpendingsAveragesResponse {

    private String category;
    private Map<String, String> monthsAndAmounts;

}
