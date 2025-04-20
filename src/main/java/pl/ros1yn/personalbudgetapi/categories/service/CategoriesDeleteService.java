package pl.ros1yn.personalbudgetapi.categories.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.categories.repository.CategoriesRepository;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriesDeleteService {

    private final CategoriesRepository categoriesRepository;
    private ClassFinder classFinder;

    public void deleteCategory(Integer categoryId) {

        Categories existingCategory = classFinder.findCategory(categoryId);

        categoriesRepository.delete(existingCategory);
        log.info("Category has been deleted");
    }

}
