package pl.ros1yn.personalbudgetapi.categories.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ros1yn.personalbudgetapi.categories.dto.CategoriesResponse;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.categories.service.CategoriesDeleteService;
import pl.ros1yn.personalbudgetapi.categories.service.CategoriesGetService;
import pl.ros1yn.personalbudgetapi.categories.service.CategoriesPatchService;
import pl.ros1yn.personalbudgetapi.categories.service.CategoriesPostService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Slf4j
class CategoryController {

    private CategoriesGetService categoriesGetService;
    private CategoriesPostService categoriesPostService;
    private CategoriesDeleteService categoriesDeleteService;
    private CategoriesPatchService categoriesPatchService;

    @GetMapping("/")
    ResponseEntity<List<CategoriesResponse>> getAllCategories() {

        log.info("Recived request for getAllCategories.");
        return categoriesGetService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoriesResponse> getCategoryById(@PathVariable Integer categoryId) {

        log.info("Recived request for getCategoryById with id: {}", categoryId);
        return categoriesGetService.getCategoryById(categoryId);
    }

    @PostMapping("/")
    ResponseEntity<CategoriesResponse> createCategory(@RequestBody Categories categoryName) {

        log.info("Recived request for createCategory with id: {}", categoryName);
        return categoriesPostService.createCategory(categoryName);
    }

    @DeleteMapping("/{categoryId}")
    void deleteCategory(@PathVariable Integer categoryId) {

        log.info("Recived request for deleteCategory with id: {}", categoryId);
        categoriesDeleteService.deleteCategory(categoryId);
    }

    @PatchMapping("/{categoryId}")
    ResponseEntity<CategoriesResponse> patchCategory(@PathVariable Integer categoryId, @RequestBody String categoryName) {

        log.info("Recived request for patchCategory with id and name: {} | {} ", categoryId, categoryName);
        return categoriesPatchService.patchCategory(categoryId, categoryName);
    }
}
