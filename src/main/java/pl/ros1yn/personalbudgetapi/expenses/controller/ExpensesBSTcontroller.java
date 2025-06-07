package pl.ros1yn.personalbudgetapi.expenses.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.service.ExpensesBSTservice;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expensesBST")
@AllArgsConstructor
@Slf4j
public class ExpensesBSTcontroller {

    private final ExpensesBSTservice expensesBSTservice;


    @GetMapping("/allsorted")
    public ResponseEntity<List<Expenses>> getAllSorted() {

        log.info("Recived request for getAllSorted.");
        List<Expenses> sorted = expensesBSTservice.getAllSortedByDate();

        return ResponseEntity.ok(sorted);
    }


    @GetMapping("/byDate")
    public ResponseEntity<Expenses> getByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        log.info("Recived request for getByDate with date: {}.", date);

        Expenses found = expensesBSTservice.getByDate(date);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(found);
    }


    @DeleteMapping("/byDate")
    public ResponseEntity<Void> deleteByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        log.info("Recived request for deleteByDate with date: {}.", date);

        expensesBSTservice.deleteByDate(date);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/preorder")
    public ResponseEntity<List<Expenses>> getPreorder() {

        log.info("Recived request for getPreorder.");

        List<Expenses> list = expensesBSTservice.getPreorder();
        return ResponseEntity.ok(list);
    }

    /**
     * 6. Postorder (lista wydatków w kolejności postorder).
     */
    @GetMapping("/postorder")
    public ResponseEntity<List<Expenses>> getPostorder() {

        log.info("Recived reqeust for getPostorder.");

        List<Expenses> list = expensesBSTservice.getPostorder();
        return ResponseEntity.ok(list);
    }
}
