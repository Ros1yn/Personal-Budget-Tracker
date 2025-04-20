package pl.ros1yn.personalbudgetapi.expenses.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesResponse;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpensesMapper {

    public static ExpensesResponse mapToResponse(Expenses expenses){

        return ExpensesResponse.builder()
                .id(expenses.getId())
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .expenseDate(expenses.getExpenseDate())
                .categoryName(expenses.getCategory().getCategoryName())
                .build();
    }

}
