package pl.ros1yn.personalbudgetapi.analytics.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DailySpendingsResponse {

    private String categoryName;
    private Map<String, String> spendings;

}
