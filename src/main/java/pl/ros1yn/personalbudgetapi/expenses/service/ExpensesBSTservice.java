package pl.ros1yn.personalbudgetapi.expenses.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.expenses.bst.ExpenseBST;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class ExpensesBSTservice {

    private final ExpensesRepository expensesRepository;
    private final ExpenseBST bst = new ExpenseBST();

    @PostConstruct
    public void initBST() {
        List<Expenses> allExpenses = expensesRepository.findAll();
        allExpenses.forEach(bst::insert);
    }


    public List<Expenses> getAllSortedByDate() {
        return bst.inorder();
    }


    public Expenses getByDate(LocalDate date) {
        return bst.find(date);
    }


    public void deleteByDate(LocalDate date) {

        bst.delete(date);

        List<Expenses> toDelete = expensesRepository.findAll().stream()
                .filter(e -> e.getExpenseDate().isEqual(date))
                .toList();
        if (!toDelete.isEmpty()) {
            expensesRepository.deleteAll(toDelete);
        }
    }


    public List<Expenses> getPreorder() {
        return bst.preorder();
    }


    public List<Expenses> getPostorder() {
        return bst.postorder();
    }
}
