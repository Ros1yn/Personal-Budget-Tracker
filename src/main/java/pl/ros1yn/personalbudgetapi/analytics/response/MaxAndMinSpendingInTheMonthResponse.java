package pl.ros1yn.personalbudgetapi.analytics.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaxAndMinSpendingInTheMonthResponse {

    private String spendingAmount;
}
