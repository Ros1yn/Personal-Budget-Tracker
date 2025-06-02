package pl.ros1yn.personalbudgetapi.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;

@Data
@Builder
public class TrendRequest {

    private String categoryName;
    private YearMonth dateFrom;
    private YearMonth dateTo;

}
