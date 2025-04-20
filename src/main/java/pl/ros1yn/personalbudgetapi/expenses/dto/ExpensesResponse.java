package pl.ros1yn.personalbudgetapi.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpensesResponse {

    private Integer id;
    private Double amount;
    private LocalDate expenseDate;
    private String description;
    private String categoryName;

}
