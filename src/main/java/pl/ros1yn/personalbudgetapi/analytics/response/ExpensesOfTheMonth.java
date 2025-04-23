package pl.ros1yn.personalbudgetapi.analytics.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ExpensesOfTheMonth {

    private String category;
    private Map<String, List<Double>> expenses;

}
