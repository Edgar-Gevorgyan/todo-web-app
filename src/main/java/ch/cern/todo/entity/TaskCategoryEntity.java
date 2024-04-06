package ch.cern.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TASK_CATEGORIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "CATEGORY_DESCRIPTION", length = 500)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();
}
