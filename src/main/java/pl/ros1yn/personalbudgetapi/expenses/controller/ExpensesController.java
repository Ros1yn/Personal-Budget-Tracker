package pl.ros1yn.personalbudgetapi.expenses.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesRequest;
import pl.ros1yn.personalbudgetapi.expenses.dto.ExpensesResponse;
import pl.ros1yn.personalbudgetapi.expenses.service.ExpensePostService;
import pl.ros1yn.personalbudgetapi.expenses.service.ExpensesGetService;
import pl.ros1yn.personalbudgetapi.expenses.service.ExpensesPatchService;

import java.util.List;

@RestController
@RequestMapping("/exenses")
@AllArgsConstructor
@Slf4j
class ExpensesController {

    private ExpensesGetService expensesGetService;
    private ExpensePostService expensePostService;
    private ExpensesPatchService expensesPatchService;

    @GetMapping("/")
    ResponseEntity<List<ExpensesResponse>> getAllExpenses() {

        log.info("Recived request for getAllExpenses.");
        return expensesGetService.getAllExpenses();
    }

    @GetMapping("/{expensesId}")
    ResponseEntity<ExpensesResponse> getExpensesById(@PathVariable Integer expensesId) {

        log.info("Recived request for getExpensesById with id: {}", expensesId);
        return expensesGetService.getExpensesById(expensesId);
    }

    @PostMapping("/")
    ResponseEntity<ExpensesResponse> createExpense(@RequestBody ExpensesRequest expensesRequest) {

        log.info("Recived expensesRequest for createExpense with id: {}", expensesRequest);
        return expensePostService.createExpenses(expensesRequest);
    }

    @PatchMapping("/{expensesId}")
    ResponseEntity<ExpensesResponse> patchExpenses(@PathVariable Integer expensesId, @RequestBody ExpensesRequest expensesRequest) {

        log.info("Recived expensesRequest for patchExpenses with id and body:: {} | {}", expensesId, expensesRequest);
        return expensesPatchService.patchExpenses(expensesId, expensesRequest);
    }
}
