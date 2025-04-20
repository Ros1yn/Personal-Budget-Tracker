package pl.ros1yn.personalbudgetapi.categories.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.categories.dto.CategoriesResponse;
import pl.ros1yn.personalbudgetapi.categories.mapper.CategoriesMapper;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriesPatchService {

    private ClassFinder classFinder;

    @Transactional
    public ResponseEntity<CategoriesResponse> patchCategory(Integer categoryId, String categoryName) {

        Categories existingCategory = classFinder.findCategory(categoryId);

        Optional.of(categoryName)
                .ifPresent(existingCategory::setCategoryName);

        log.info("Category name has been changed");
        return ResponseEntity.ok(CategoriesMapper.mapToResponse(existingCategory));
    }
}
