package pl.ros1yn.personalbudgetapi.categories.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriesResponse {

    private int id;
    private String categoryName;
}
