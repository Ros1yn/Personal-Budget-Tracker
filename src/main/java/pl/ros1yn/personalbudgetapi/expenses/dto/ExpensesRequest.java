package pl.ros1yn.personalbudgetapi.expenses.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpensesRequest {

    private Double amount;
    private LocalDate expenseDate;
    private String description;
    private Integer categoryId;

}
