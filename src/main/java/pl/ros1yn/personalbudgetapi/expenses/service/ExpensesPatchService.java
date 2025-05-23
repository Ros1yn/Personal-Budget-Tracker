package pl.ros1yn.personalbudgetapi.expenses.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesRequest;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesResponse;
import pl.ros1yn.personalbudgetapi.expenses.mapper.ExpensesMapper;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ExpensesPatchService {

    private ClassFinder classFinder;

    public ResponseEntity<ExpensesResponse> patchExpenses(Integer expensesId, ExpensesRequest expensesRequest) {

        Expenses existingExpenses = classFinder.findExpenses(expensesId);

        Optional.of(expensesRequest.getAmount())
                .ifPresent(existingExpenses::setAmount);

        Optional.of(expensesRequest.getDescription())
                .ifPresent(existingExpenses::setDescription);

        Optional.of(expensesRequest.getExpenseDate())
                .ifPresent(existingExpenses::setExpenseDate);

        Optional.of(expensesRequest.getCategoryId())
                .ifPresent(expensesRequest::setCategoryId);

        return ResponseEntity.ok(ExpensesMapper.mapToResponse(existingExpenses));
    }
}
