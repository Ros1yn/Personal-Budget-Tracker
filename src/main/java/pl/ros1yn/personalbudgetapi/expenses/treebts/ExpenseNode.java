package pl.ros1yn.personalbudgetapi.expenses.treebts;

import lombok.Data;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import java.time.LocalDate;

@Data
public class ExpenseNode {

    private Expenses expense;
    private ExpenseNode left;
    private ExpenseNode right;

    public LocalDate getKeyDate() {
        return expense.getExpenseDate();
    }

}
