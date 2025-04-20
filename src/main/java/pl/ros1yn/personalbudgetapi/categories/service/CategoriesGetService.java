package pl.ros1yn.personalbudgetapi.categories.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.ros1yn.personalbudgetapi.categories.dto.CategoriesResponse;
import pl.ros1yn.personalbudgetapi.categories.mapper.CategoriesMapper;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;
import pl.ros1yn.personalbudgetapi.categories.repository.CategoriesRepository;
import pl.ros1yn.personalbudgetapi.utils.ClassFinder;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoriesGetService {

    private final CategoriesRepository categoriesRepository;
    private ClassFinder classFinder;

    public ResponseEntity<List<CategoriesResponse>> getAllCategories() {

        return ResponseEntity.ok(categoriesRepository.findAll().stream()
                .map(CategoriesMapper::mapToResponse)
                .toList());
    }

    public ResponseEntity<CategoriesResponse> getCategoryById(Integer categoryId) {

        Categories foundCategory = classFinder.findCategory(categoryId);

        return ResponseEntity.ok(CategoriesMapper.mapToResponse(foundCategory));
    }

}
