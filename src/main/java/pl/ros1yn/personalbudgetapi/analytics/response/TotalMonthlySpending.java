package pl.ros1yn.personalbudgetapi.analytics.response;

import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;

@Data
@Builder
public class TotalMonthlySpending {

    private YearMonth spendingsDate;
    private String totalSpendings;

}
