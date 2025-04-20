package pl.ros1yn.personalbudgetapi.categories.model;

import jakarta.persistence.*;
import lombok.*;
import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Expenses> expenses = new ArrayList<>();

}
