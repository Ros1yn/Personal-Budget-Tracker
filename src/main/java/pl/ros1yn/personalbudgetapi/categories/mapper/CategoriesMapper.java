package pl.ros1yn.personalbudgetapi.categories.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.personalbudgetapi.categories.dto.CategoriesResponse;
import pl.ros1yn.personalbudgetapi.categories.model.Categories;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriesMapper {

    public static CategoriesResponse mapToResponse(Categories categories){
        return CategoriesResponse.builder()
                .id(categories.getId())
                .categoryName(categories.getCategoryName())
                .build();
    }

}
