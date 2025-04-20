package pl.ros1yn.personalbudgetapi.categories.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.categories.dto.CategoriesResponse;
import pl.ros1yn.personalbudgetapi.categories.mapper.CategoriesMapper;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.categories.repository.CategoriesRepository;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriesPostService {

    private final CategoriesRepository categoriesRepository;

    public ResponseEntity<CategoriesResponse> createCategory(Categories createdCategory) {

        Categories savedCategory = categoriesRepository.save(createdCategory);

        log.info("Category has been created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriesMapper.mapToResponse(savedCategory));
    }

}
