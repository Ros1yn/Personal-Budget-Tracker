package pl.ros1yn.personalbudgetapi.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.categories.repository.CategoriesRepository;
import pl.ros1yn.personalbudgetapi.exception.CategoryNotFoundException;
import pl.ros1yn.personalbudgetapi.exception.ExpensesNotFoundException;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;
import pl.ros1yn.personalbudgetapi.expenses.repository.ExpensesRepository;

@Component
@AllArgsConstructor
@Slf4j
public class ClassFinder {

    private final CategoriesRepository categoriesRepository;
    private final ExpensesRepository expensesRepository;

    public Categories findCategory(Integer categoryId) {

        return categoriesRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category not found");
                    return new CategoryNotFoundException();
                });
    }

    public Expenses findExpenses(Integer expensesId) {

        return expensesRepository.findById(expensesId)
                .orElseThrow(() -> {
                    log.error("Expenses not found");
                    return new ExpensesNotFoundException();
                });
    }

}
