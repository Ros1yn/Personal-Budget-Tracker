package pl.ros1yn.personalbudgetapi.expenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {
}
