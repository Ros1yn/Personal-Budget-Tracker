package pl.ros1yn.personalbudgetapi.expenses.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesResponse;
import pl.ros1yn.personalbudgetapi.expenses.mapper.ExpensesMapper;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ExpensesGetService {

    private final ExpensesRepository expensesRepository;
    private ClassFinder classFinder;

    public ResponseEntity<List<ExpensesResponse>> getAllExpenses() {

        return ResponseEntity.ok(expensesRepository.findAll().stream()
                .map(ExpensesMapper::mapToResponse)
                .toList());
    }

    public ResponseEntity<ExpensesResponse> getExpensesById(Integer expensesId) {

        Expenses foundedExpenses = classFinder.findExpenses(expensesId);
        return ResponseEntity.ok(ExpensesMapper.mapToResponse(foundedExpenses));
    }


}
