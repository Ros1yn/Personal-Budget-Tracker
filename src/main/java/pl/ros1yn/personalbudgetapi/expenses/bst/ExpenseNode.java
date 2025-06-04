package pl.ros1yn.personalbudgetapi.expenses.bst;

import lombok.Data;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

import java.time.LocalDate;

@Data
public class ExpenseNode {

    private Expenses expense;
    private ExpenseNode left;
    private ExpenseNode right;

    public ExpenseNode(Expenses expense) {
        this.expense = expense;
    }

    public LocalDate getKeyDate() {
        return expense.getExpenseDate();
    }

}
