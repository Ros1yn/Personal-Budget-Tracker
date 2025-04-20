package pl.ros1yn.personalbudgetapi.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
