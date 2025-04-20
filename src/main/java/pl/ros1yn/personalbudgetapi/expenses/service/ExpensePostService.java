package pl.ros1yn.personalbudgetapi.expenses.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesRequest;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesResponse;
import pl.ros1yn.personalbudgetapi.expenses.mapper.ExpensesMapper;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

@Service
@AllArgsConstructor
@Slf4j
public class ExpensePostService {

    private final ExpensesRepository expensesRepository;
    private final ClassFinder classFinder;

    public ResponseEntity<ExpensesResponse> createExpenses(ExpensesRequest request){

        Categories existingCategory = classFinder.findCategory(request.getCategoryId());

        Expenses createdExpenses = Expenses.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .expenseDate(request.getExpenseDate())
                .category(existingCategory)
                .build();


        Expenses savedExpenses = expensesRepository.save(createdExpenses);

        return ResponseEntity.status(HttpStatus.CREATED).body(ExpensesMapper.mapToResponse(savedExpenses));
    }

}
